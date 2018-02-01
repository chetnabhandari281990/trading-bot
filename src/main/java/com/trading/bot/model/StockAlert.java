package com.trading.bot.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="stockAlert")
public class StockAlert implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long stockAlertId;

	@NotBlank(message="productId is required")
	private String productId;

	@NotNull(message="buyPrice is required")
	private BigDecimal buyPrice;
	
	private BigDecimal upperPrice;
	
	private BigDecimal lowerPrice;
	
	private boolean isActive;
	
	private Long updatedAt;
	
	private Long createdAt;


	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getStockAlertId() {
		return stockAlertId;
	}

	public void setStockRefId(Long stockAlertId) {
		this.stockAlertId = stockAlertId;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}

	

}
