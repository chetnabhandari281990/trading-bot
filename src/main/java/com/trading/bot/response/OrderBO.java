package com.trading.bot.response;

import java.math.BigDecimal;

public class OrderBO {
	private Long orderId;

	private Long stockAlertId;

	private BigDecimal closetAtPrice;

	private BigDecimal boughtAtPrice;

	private String positionId;

	private String state;
	
	private String productId;

	private BigDecimal buyPrice;
	
	private BigDecimal upperPrice;
	
	private BigDecimal lowerPrice;
	
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getStockAlertId() {
		return stockAlertId;
	}

	public void setStockAlertId(Long stockAlertId) {
		this.stockAlertId = stockAlertId;
	}

	public BigDecimal getClosetAtPrice() {
		return closetAtPrice;
	}

	public void setClosetAtPrice(BigDecimal closetAtPrice) {
		this.closetAtPrice = closetAtPrice;
	}

	public BigDecimal getBoughtAtPrice() {
		return boughtAtPrice;
	}

	public void setBoughtAtPrice(BigDecimal boughtAtPrice) {
		this.boughtAtPrice = boughtAtPrice;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public BigDecimal getUpperPrice() {
		return upperPrice;
	}

	public void setUpperPrice(BigDecimal upperPrice) {
		this.upperPrice = upperPrice;
	}

	public BigDecimal getLowerPrice() {
		return lowerPrice;
	}

	public void setLowerPrice(BigDecimal lowerPrice) {
		this.lowerPrice = lowerPrice;
	}

}
