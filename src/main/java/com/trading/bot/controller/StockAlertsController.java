package com.trading.bot.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trading.bot.exception.TradingBotRunTimeException;
import com.trading.bot.model.StockAlert;
import com.trading.bot.request.StockAlertsRequest;
import com.trading.bot.response.SuccessResponse;
import com.trading.bot.service.StockAlertsService;
import com.trading.bot.utils.CommonUtils;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/alerts")
public class StockAlertsController {
	

	private static final Logger logger = LoggerFactory.getLogger(StockAlertsController.class);
	
	@Autowired
	private StockAlertsService stockSvc;
	
	
	@ApiOperation(value = "Create a new alert")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createStockAlert(@Valid @RequestBody StockAlertsRequest stockAlertsRequest) throws Exception {
		logger.debug("createStockAlert :: entering with request: stockAlertsRequest {}", stockAlertsRequest);
        CommonUtils.validateRequest(stockAlertsRequest);
        
        StockAlert stockAlert = stockSvc.processCreateAlertRequest(stockAlertsRequest);
        SuccessResponse<StockAlert> result = new SuccessResponse<StockAlert>(stockAlert);

        return ResponseEntity.ok(result);
        
	}


	
	@ApiOperation(value = "Fetch alert by alert-id")
	@RequestMapping(value="/{alertId}",method = RequestMethod.GET)	
	public ResponseEntity<?> fetchAlertById(@PathVariable Long alertId) throws Exception {
		logger.debug("fetchAlertById :: entering with alertId: {}", alertId);
        StockAlert existingStockAlert = stockSvc.findById(alertId);
        if(existingStockAlert == null) {
        	throw new TradingBotRunTimeException("TBT404", "No alert found by this id");
        }
        SuccessResponse<StockAlert> result = new SuccessResponse<StockAlert>(existingStockAlert);
        return ResponseEntity.ok(result);
        
	}
	
	
	@ApiOperation(value = "Fetch active alert for product-id")
	@RequestMapping(method = RequestMethod.GET)	
	public ResponseEntity<?> fetchAlert(@RequestParam String productId) throws Exception {
		logger.debug("createStock :: entering with request: ");
        StockAlert existingStockAlert = stockSvc.findByProductIdAndStatus(productId, true);
        SuccessResponse<StockAlert> result = new SuccessResponse<StockAlert>(existingStockAlert);
        return ResponseEntity.ok(result);
        
	}
	


	
	

}
