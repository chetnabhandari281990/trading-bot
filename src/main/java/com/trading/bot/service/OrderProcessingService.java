package com.trading.bot.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.bot.external.request.BuyOrder;
import com.trading.bot.external.request.InvestingAmount;
import com.trading.bot.utils.AppConstants;

@Service
public class OrderProcessingService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Value("${bux.buy.url}")
	private String buyUrl;
	
	@Value("${bux.delete.url}")
	private String deleteUrl;
	
	@Value("${authorization.key}")
	private String authHeader;
	
	
	public ResponseEntity<String> buyOrder(String productId) throws JsonParseException, JsonMappingException, IOException {
		// Using a default investing amount
		InvestingAmount invAmt = new InvestingAmount(AppConstants.CURRENCY,getNumberOfDecimalPlaces(AppConstants.DEFAULT_INVESTING_AMMOUNT), AppConstants.DEFAULT_INVESTING_AMMOUNT);
		BuyOrder buyOrder = new BuyOrder(productId, invAmt,AppConstants.LEVERAGE, AppConstants.DIRECTION);
		
		HttpHeaders headers = getHeaders();
		objectMapper.writeValueAsString(buyOrder);
		HttpEntity<BuyOrder> entity = new HttpEntity<BuyOrder>(buyOrder, headers);
		ResponseEntity<String> res = restTemplate.exchange(buyUrl, HttpMethod.POST, entity, String.class);
		return res;
		

	}
	
	int getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
	    String string = bigDecimal.stripTrailingZeros().toPlainString();
	    int index = string.indexOf(".");
	    return index < 0 ? 0 : string.length() - index - 1;
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AppConstants.AUTHORIZATION, authHeader);
        headers.set("Accept-Language", AppConstants.ACCEPT_LANGUAGE);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}
	
	public ResponseEntity<String> closeOrder(String positionId) {
		HttpEntity entity = new HttpEntity(null, getHeaders());
		ResponseEntity<String> res = restTemplate.exchange( MessageFormat.format(deleteUrl, positionId), HttpMethod.DELETE, entity, String.class);
		return res;
		
		
	}

}
