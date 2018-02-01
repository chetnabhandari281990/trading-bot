package com.trading.bot.websocket;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.bot.external.request.SubscriptionRequest;
import com.trading.bot.external.response.StockBody;
import com.trading.bot.external.response.WebSocketResponse;
import com.trading.bot.model.StockAlert;
import com.trading.bot.service.FeedDataProcessingService;
import com.trading.bot.utils.AppConstants;
import com.trading.bot.utils.CommonUtils;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

@Component
public class BuxWebSocketListener extends WebSocketListener {
	
	Logger logger = LoggerFactory.getLogger(BuxWebSocketListener.class);
	

	private static final int NORMAL_CLOSURE_STATUS = 1000;

	ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private FeedDataProcessingService feedProceesingSvc;

	@Override
	public void onOpen(WebSocket webSocket, Response response) {
		System.out.println(response);
	}

	@Override
	public void onMessage(WebSocket webSocket, String text) {
		try {
			WebSocketResponse res = objectMapper.readValue(text, WebSocketResponse.class);
			if (res != null && res.getT()!= null && res.getT().equalsIgnoreCase(AppConstants.CONNECTED)) {
				logger.info("========== Connected to real time feed ===========");
				SubscriptionRequest subReq = new SubscriptionRequest(CommonUtils.getSubscriptionList(), new ArrayList<>());
				webSocket.send(objectMapper.writeValueAsString(subReq));
			} else if(res != null && res.getT()!= null && res.getT().equalsIgnoreCase(AppConstants.CONNECT_FAILED)){
				// you may trigger email to the dev here
				logger.error("==============Connection failed============ res {}", res.getBody());
			} else {
				String actionToTake = feedProceesingSvc.determineAction(res);
				if(!StringUtils.isEmpty(actionToTake)) {
					StockBody sb = objectMapper.readValue(objectMapper.writeValueAsString(res.getBody()), StockBody.class);
					
					StockAlert savedStockAlert = CommonUtils.stockActiveAlertsMap.get(sb.getSecurityId());
					
					switch (actionToTake) {
					case AppConstants.ACTION_TO_TAKE.BUY:
						feedProceesingSvc.buyOrderAndUpdateStatus(sb, savedStockAlert);
						break;
					case AppConstants.ACTION_TO_TAKE.CLOSE:
						feedProceesingSvc.closeOrderAndUpdateStatus(sb, savedStockAlert, webSocket);
						break;

					}
				}
			}
		} catch (Exception e) {
			logger.error("Error in BuxWebSocketListener.onMessage ", e);
		}
	}
	

	@Override
	public void onClosing(WebSocket webSocket, int code, String reason) {
		webSocket.close(NORMAL_CLOSURE_STATUS, null);
		logger.info("Closing: " + code + " " + reason);
	}

	@Override
	public void onFailure(WebSocket webSocket, Throwable t, Response response) {
		logger.error("Error encountered in BuxWebSocketListener", t);
	}
	

}