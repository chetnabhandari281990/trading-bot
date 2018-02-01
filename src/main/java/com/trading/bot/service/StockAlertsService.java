package com.trading.bot.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trading.bot.model.StockAlert;
import com.trading.bot.repository.StockAlertsRepository;
import com.trading.bot.request.StockAlertsRequest;
import com.trading.bot.utils.AppConstants;
import com.trading.bot.utils.CommonUtils;
import com.trading.bot.websocket.WebSocketService;

@Service
public class StockAlertsService {
	
	@Autowired
	private StockAlertsRepository stockAlertsRepo;
	
	
	@Autowired
	private WebSocketService webSocketService;
	
	public StockAlert processCreateAlertRequest(StockAlertsRequest stockAlertsRequest) throws JsonProcessingException {
		
		// Check if there are any existing alerts set for the product. If present mark it inactive.
        StockAlert existingStockAlert = findByProductIdAndStatus(stockAlertsRequest.getProductId(), true);
        if(existingStockAlert != null) {
        	saveOrUpdateStockAlert(existingStockAlert, AppConstants.INACTIVE);
        }
        
        // Create new stock alert.
        StockAlert stockAlert = new StockAlert();
        BeanUtils.copyProperties(stockAlertsRequest, stockAlert);
        stockAlert = saveOrUpdateStockAlert(stockAlert, AppConstants.ACTIVE);
        
        // subscribe to web socket for product.
        webSocketService.configureWebSocketClient();
		return stockAlert;
	}
	
	
	public StockAlert saveOrUpdateStockAlert(StockAlert sa, boolean status) {
		sa.setActive(status);
		if(sa.getStockAlertId() == null) {
			sa.setCreatedAt(CommonUtils.getCurrentTime());
		} 
		sa.setUpdatedAt(CommonUtils.getCurrentTime());
		return stockAlertsRepo.save(sa);
	}
	
	
	public StockAlert findByProductIdAndStatus(String productId, boolean isActive) {
		return stockAlertsRepo.findByProductIdAndIsActive(productId, isActive);
	}
	
	public StockAlert findById(Long alertId) {
		return stockAlertsRepo.findOne(alertId);
	}
	
	public List<StockAlert> findByIsActive(boolean isActive) {
		return stockAlertsRepo.findByIsActive(isActive);
	}
	
	
	

}
