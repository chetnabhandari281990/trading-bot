package com.trading.bot;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.bot.external.response.BuyOrderResponse;
import com.trading.bot.service.OrderProcessingService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TradingBotApplication.class)
@WebAppConfiguration
public class RestCallsTest {
	
	@Autowired
	private OrderProcessingService stockService;
	
	
	ObjectMapper objMapper = new ObjectMapper();
	
	
	@Test
	public void buyOrder() throws Exception {
		ResponseEntity<String> res = stockService.buyOrder("sb26496");
		Assert.assertTrue(res.getStatusCode().equals(HttpStatus.OK));
	}
	
	
	@Test
	public void deleteOrder() throws Exception {
		ResponseEntity<String> res = stockService.buyOrder("sb26496");
		if(res.getBody() != null) {
			BuyOrderResponse buyOrderRes = objMapper.readValue(res.getBody(), BuyOrderResponse.class);
		ResponseEntity<String> delRes = stockService.closeOrder(buyOrderRes.getPositionId());
		Assert.assertTrue(delRes.getStatusCode().equals(HttpStatus.OK));
		}
		
		
	}
	
	
	
	
	
}
	
	
	
