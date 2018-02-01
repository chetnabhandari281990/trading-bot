package com.trading.bot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.bot.enums.StockActivityStatus;
import com.trading.bot.external.request.SubscriptionRequest;
import com.trading.bot.external.response.BuyOrderResponse;
import com.trading.bot.external.response.StockBody;
import com.trading.bot.external.response.WebSocketResponse;
import com.trading.bot.model.Orders;
import com.trading.bot.model.StockAlert;
import com.trading.bot.repository.OrderRepository;
import com.trading.bot.utils.AppConstants;
import com.trading.bot.utils.AppConstants.ACTION_TO_TAKE;
import com.trading.bot.utils.CommonUtils;

import okhttp3.WebSocket;

@Service
public class FeedDataProcessingService {
	Logger logger = LoggerFactory.getLogger(FeedDataProcessingService.class);

	@Autowired
	private StockAlertsService stockAlertsService;
	
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderProcessingService orderProcessingService;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	public String determineAction(WebSocketResponse res) throws Exception{
		String actionToTake= null;
		
		if (res.getT() != null && res.getT().equalsIgnoreCase(AppConstants.TRADING_QUOTE)){
			StockBody sb = objectMapper.readValue(objectMapper.writeValueAsString(res.getBody()), StockBody.class);
			if(CommonUtils.stockActiveAlertsMap.containsKey(sb.getSecurityId())) {
			StockAlert savedStockAlert = CommonUtils.stockActiveAlertsMap.get(sb.getSecurityId());
			
			Orders order = orderRepository.findByStockAlertId(savedStockAlert.getStockAlertId());
			
			if (order == null && sb.getCurrentPrice().compareTo(savedStockAlert.getBuyPrice()) < 1) {
				actionToTake = ACTION_TO_TAKE.BUY;

			} else if (order != null && order.getState().equals(StockActivityStatus.BOUGHT.name())
					&& upperOrLowerLimitReached(sb, savedStockAlert)) {
				actionToTake = ACTION_TO_TAKE.CLOSE;
			} 
		} else {
			logger.debug("BuxWebSocketListener.parseResponseBody WebSocketResponse {}", res);
		}
	}
		return actionToTake;
	}
	

	

	public boolean upperOrLowerLimitReached(StockBody sb, StockAlert savedStockAlert) {
		return (savedStockAlert.getUpperPrice() != null && sb.getCurrentPrice().compareTo(savedStockAlert.getUpperPrice()) > -1)
				|| (savedStockAlert.getLowerPrice() != null && sb.getCurrentPrice().compareTo(savedStockAlert.getLowerPrice()) < 1);
	}

	public void closeOrderAndUpdateStatus(StockBody sb, StockAlert savedStockAlert, WebSocket ws) throws JsonProcessingException {
		Orders order = orderRepository.findByStockAlertId(savedStockAlert.getStockAlertId());
		
		logger.info("=========== Close Position triggered for product {} closingPrice{} caused by alert id {} ============", savedStockAlert.getProductId(),
				sb.getCurrentPrice(), savedStockAlert.getStockAlertId());
		
		ResponseEntity<String> res = orderProcessingService.closeOrder(order.getPositionId());
		
		if (res.getBody() != null && res.getStatusCode().equals(HttpStatus.OK)) {
			order.setUpdatedAt(CommonUtils.getCurrentTime());
			order.setClosetAtPrice(sb.getCurrentPrice());
			order.setState(StockActivityStatus.CLOSED.name());
			orderRepository.save(order);
			
			// Alert can be marked inactive and unsubscribed as its work is done
			stockAlertsService.saveOrUpdateStockAlert(savedStockAlert, AppConstants.INACTIVE);
			
			// Update static map of active alerts
			CommonUtils.stockActiveAlertsMap.remove(savedStockAlert.getStockAlertId());
			SubscriptionRequest subReq = new SubscriptionRequest(new ArrayList<>(), Arrays.asList(AppConstants.TRADING_PREFIX + savedStockAlert.getProductId()));
			ws.send(objectMapper.writeValueAsString(subReq));
		} else {
			logger.error("Error encountered in BuxWebSocketListener.closeOrderAndUpdateStatus res", res);
		}
	}

	public void buyOrderAndUpdateStatus(StockBody sb, StockAlert savedStockAlert) throws JsonParseException, JsonMappingException, IOException {

		logger.info("========== Buy order triggered for product {} boughtAtPrice {} caused by alert id {} =============================", savedStockAlert.getProductId(), 
				sb.getCurrentPrice(), savedStockAlert.getStockAlertId());
		
		Orders order = new Orders(null, sb.getCurrentPrice(), StockActivityStatus.BOUGHT.name(),
				savedStockAlert.getStockAlertId());
		ResponseEntity<String> res = orderProcessingService.buyOrder(sb.getSecurityId());
		if (res.getBody() != null && res.getStatusCode().equals(HttpStatus.OK)) {
			BuyOrderResponse buyOrderResponse = objectMapper.readValue(res.getBody(), BuyOrderResponse.class);
			String positionId = buyOrderResponse.getPositionId();
			
			// save positionId in db
			order.setPositionId(positionId);
			order.setCreatedAt(CommonUtils.getCurrentTime());
			order = orderRepository.save(order);

		} else {
			logger.error("Error encountered in BuxWebSocketListener.buyOrderAndUpdateStatus res", res);
		}


	}

}
