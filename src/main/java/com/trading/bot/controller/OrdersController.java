package com.trading.bot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trading.bot.model.Orders;
import com.trading.bot.repository.OrderRepository;
import com.trading.bot.response.SuccessResponse;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/v1/orders")
public class OrdersController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);
	
	@Autowired
	private OrderRepository stockActivityRepository;
	
	@ApiOperation(value = "Fetch orders for product-id")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> fetchOrderByProductId(@RequestParam String productId) throws Exception {
		logger.debug("fetchOrderByProductId :: entering with  productId: {} ", productId);
		List<Orders> orders = stockActivityRepository.findOrdersByProductId(productId);
        SuccessResponse<List<Orders>> result = new SuccessResponse<List<Orders>>(orders);
        return ResponseEntity.ok(result);
        
	}

}
