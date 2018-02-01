package com.trading.bot.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trading.bot.model.StockAlert;

public interface StockAlertsRepository extends CrudRepository<StockAlert, Long> {
	
	
	StockAlert findByProductIdAndIsActive(@Param("productId") String productId, @Param("isActive") boolean isActive);
	
	
	
	List<StockAlert> findByIsActive(@Param("isActive") boolean isActive);
	


}
