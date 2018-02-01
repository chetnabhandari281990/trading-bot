package com.trading.bot.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.trading.bot.utils.CommonUtils;

@Entity
@Table(name = "orders")
public class Orders implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderId;

	private Long stockAlertId;

	private BigDecimal closetAtPrice;

	private BigDecimal boughtAtPrice;

	private String positionId;

	private String state;

	private Long createdAt;

	private Long updatedAt;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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

	public Long getStockAlertId() {
		return stockAlertId;
	}

	public void setStockAlertId(Long stockAlertId) {
		this.stockAlertId = stockAlertId;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return CommonUtils.toJson(this);
	}

	public Orders() {
		super();
	}

	public Orders(BigDecimal closetAtPrice, BigDecimal boughtAtPrice, String state, Long stockAlertId) {
		super();
		this.closetAtPrice = closetAtPrice;
		this.boughtAtPrice = boughtAtPrice;
		this.stockAlertId = stockAlertId;
		this.state = state;
		this.createdAt = this.updatedAt = CommonUtils.getCurrentTime();
	}

}
