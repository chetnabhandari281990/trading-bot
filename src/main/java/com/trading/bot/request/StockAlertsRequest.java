package com.trading.bot.request;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.trading.bot.utils.CommonUtils;

public class StockAlertsRequest implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@NotBlank(message="productId is required")
	private String productId;

	@NotNull(message="buyPrice is required")
	private BigDecimal buyPrice;
	
	private BigDecimal upperPrice;
	
	private BigDecimal lowerPrice;
	

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

	@Override
	public String toString() {
		return CommonUtils.toJson(this);
	}
	

}
