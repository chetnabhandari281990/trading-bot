package com.trading.bot.external.request;

import java.math.BigDecimal;

public class InvestingAmount {


	private String currency;

	private Integer decimals;

	private BigDecimal amount;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getDecimals() {
		return decimals;
	}

	public void setDecimals(Integer decimals) {
		this.decimals = decimals;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public InvestingAmount(String currency, Integer decimals, BigDecimal amount) {
		super();
		this.currency = currency;
		this.decimals = decimals;
		this.amount = amount;
	}
	

	public InvestingAmount() {
		super();
	}


}
