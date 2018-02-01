package com.trading.bot.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trading.bot.model.Orders;

public interface OrderRepository extends CrudRepository<Orders, Long>{
	
	Orders findByStockAlertId(@Param("stockAlertId") Long stockAlertId);
	
	
	@Query(value = "select * from stock_alert sa,orders od where od.stock_alert_Id = sa.stock_alert_id and sa.product_id = :productId",  nativeQuery = true) 
	List<Orders> findOrdersByProductId(@Param("productId") String productId);
	

}


