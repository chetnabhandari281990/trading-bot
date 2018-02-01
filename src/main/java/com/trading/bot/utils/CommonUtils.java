package com.trading.bot.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;

import com.google.gson.Gson;
import com.trading.bot.exception.TradingBotRunTimeException;
import com.trading.bot.model.StockAlert;
import com.trading.bot.request.StockAlertsRequest;

public class CommonUtils {
	
	public static Gson gson = new Gson();

	// Map containing active alerts. Created on server startup
	public static Map<String, StockAlert> stockActiveAlertsMap;

	public static long getCurrentTime() {
		return System.currentTimeMillis();
	}
	
	public static void validateRequest(StockAlertsRequest stockAlertsRequest) {
		if((stockAlertsRequest.getUpperPrice() != null && stockAlertsRequest.getUpperPrice().compareTo(stockAlertsRequest.getBuyPrice()) != 1) || 
				(stockAlertsRequest.getLowerPrice() != null && stockAlertsRequest.getLowerPrice().compareTo(stockAlertsRequest.getBuyPrice()) != -1)) {
			throw new TradingBotRunTimeException("TB400", "Invalid stock alert request");
			
		}
		
	}
	
	public static String toJson(Object o) {
		return gson.toJson(o);
	}
	
	
	public static final List<String> getSubscriptionList() {
		List<String> stockIdList = CommonUtils.stockActiveAlertsMap.keySet().stream().map(c -> AppConstants.TRADING_PREFIX + c).collect(Collectors.toList());
		return stockIdList;
	
	}
	
	   public static boolean isError(final HttpStatus status) {
	        final HttpStatus.Series series = status.series();
	        return HttpStatus.Series.CLIENT_ERROR.equals(series)
	                || HttpStatus.Series.SERVER_ERROR.equals(series);
	    }
	

}
