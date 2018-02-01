package com.trading.bot.external.request;

public class BuyOrder {
	
	private String productId;
	
	private InvestingAmount investingAmount;
	
	private int leverage;
	
	private String direction;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public InvestingAmount getInvestingAmount() {
		return investingAmount;
	}

	public void setInvestingAmount(InvestingAmount investingAmount) {
		this.investingAmount = investingAmount;
	}

	public int getLeverage() {
		return leverage;
	}

	public void setLeverage(int leverage) {
		this.leverage = leverage;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public BuyOrder(String productId, InvestingAmount investingAmount, int leverage, String direction) {
		super();
		this.productId = productId;
		this.investingAmount = investingAmount;
		this.leverage = leverage;
		this.direction = direction;
	}

	public BuyOrder() {
		super();
	}

	
	

}
