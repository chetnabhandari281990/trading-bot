package com.trading.bot;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.bot.enums.StockActivityStatus;
import com.trading.bot.external.response.StockBody;
import com.trading.bot.external.response.WebSocketResponse;
import com.trading.bot.model.Orders;
import com.trading.bot.model.StockAlert;
import com.trading.bot.repository.OrderRepository;
import com.trading.bot.request.StockAlertsRequest;
import com.trading.bot.service.FeedDataProcessingService;
import com.trading.bot.service.StockAlertsService;
import com.trading.bot.utils.AppConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TradingBotApplication.class)
@WebAppConfiguration
public class FeedDataProcessingServiceTest {
	
	@Autowired
	private FeedDataProcessingService feedDataProcessingService;
	
	@Autowired
	private StockAlertsService stockAlertsService;
	
    @Autowired
    private OrderRepository orderRepository;
	
	ObjectMapper objMapper = new ObjectMapper();
	
	@Test
	public void buyOrder() throws Exception {
		StockAlertsRequest sar = new StockAlertsRequest();
		sar.setBuyPrice(new BigDecimal("2000"));
		sar.setLowerPrice(new BigDecimal("1900"));
		sar.setUpperPrice(new BigDecimal("3000"));
		sar.setProductId("testChetna");
		StockAlert stockAlert = stockAlertsService.processCreateAlertRequest(sar);
		
		StockBody sb = new StockBody();
		sb.setCurrentPrice(new BigDecimal("2000"));
		sb.setSecurityId("testChetna");
		WebSocketResponse<StockBody> wsRes = new WebSocketResponse<>();
		wsRes.setT(AppConstants.TRADING_QUOTE);
		wsRes.setBody(sb);
		String actionToTake = feedDataProcessingService.determineAction(wsRes);
		
		// Inactivate alert when test done
		stockAlertsService.saveOrUpdateStockAlert(stockAlert, AppConstants.INACTIVE);
		Assert.assertTrue(actionToTake.equals("BUY"));
	}
	
	
	@Test
	public void closeOrder() throws Exception {
		StockAlertsRequest sar = new StockAlertsRequest();
		sar.setBuyPrice(new BigDecimal("2000"));
		sar.setLowerPrice(new BigDecimal("1900"));
		sar.setUpperPrice(new BigDecimal("3000"));
		sar.setProductId("testChetna");
		StockAlert stockAlert = stockAlertsService.processCreateAlertRequest(sar);
		
		// buy order dummy entry
		Orders order = new Orders();
		order.setBoughtAtPrice(new BigDecimal("2000"));
		order.setState(StockActivityStatus.BOUGHT.name());
		order.setStockAlertId(stockAlert.getStockAlertId());
		orderRepository.save(order);
		
		StockBody sb = new StockBody();
		sb.setCurrentPrice(new BigDecimal("30001"));
		sb.setSecurityId("testChetna");
		WebSocketResponse<StockBody> wsRes = new WebSocketResponse<>();
		wsRes.setT(AppConstants.TRADING_QUOTE);
		wsRes.setBody(sb);
		String actionToTake = feedDataProcessingService.determineAction(wsRes);
		
		// Inactivate alert when test done
		stockAlertsService.saveOrUpdateStockAlert(stockAlert, AppConstants.INACTIVE);
		Assert.assertTrue(actionToTake.equals("CLOSE"));
		
	}
	
	
	

}
