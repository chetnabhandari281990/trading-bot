package com.trading.bot.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.bot.external.request.SubscriptionRequest;
import com.trading.bot.model.StockAlert;
import com.trading.bot.service.StockAlertsService;
import com.trading.bot.utils.AppConstants;
import com.trading.bot.utils.CommonUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;


@Service
public class WebSocketService {
	
	Logger logger = LoggerFactory.getLogger(WebSocketService.class);
	
	@Autowired
	private StockAlertsService stockAlertsSvc;
	
	
	@Autowired
	BuxWebSocketListener listener;
	
	private WebSocket ws;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Value("${subscription.url}")
	private String webSocketUrl;
	
	@Value("${authorization.key}")
	private String authHeader;
	
	
	/* Defining postconstruct so that all currently active alerts are subscribed to on server startup*/
	@PostConstruct
	public void configureWebSocketClient() throws JsonProcessingException {
		List<StockAlert> stockAlerts = stockAlertsSvc.findByIsActive(true);
		if(!CollectionUtils.isEmpty(stockAlerts)) {
			populatActiveAlertsMap(stockAlerts);
			connectToWebSocket();
			
		}
	}


	private void populatActiveAlertsMap(List<StockAlert> stockAlerts) {
		Map<String, StockAlert> stockAlertsMap = stockAlerts.stream().collect(Collectors.toMap(x -> x.getProductId(), x -> x));
		 // creating an in memory static map for alerts so that we dont have to query db each time.
		 CommonUtils.stockActiveAlertsMap = stockAlertsMap;
	}


	private void connectToWebSocket() throws JsonProcessingException {
		OkHttpClient client = new OkHttpClient();
		
		Request request = new Request.Builder().url(webSocketUrl).addHeader(AppConstants.AUTHORIZATION, authHeader).addHeader("Accept-Language", AppConstants.ACCEPT_LANGUAGE).build();
		 
		if(ws != null) {
			// Update existing ws with latest subscription
			logger.info("===========Updating feed subscription list=========");
			SubscriptionRequest subReq = new SubscriptionRequest(CommonUtils.getSubscriptionList(), new ArrayList<>());
			ws.send(objectMapper.writeValueAsString(subReq));
		} else {
			// Create new socket connection
			ws = client.newWebSocket(request, listener); 
		}
		
		client.dispatcher().executorService().shutdown();
	}

}
