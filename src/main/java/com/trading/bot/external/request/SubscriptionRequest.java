package com.trading.bot.external.request;

import java.util.List;

public class SubscriptionRequest {
	
	public SubscriptionRequest(List<String> subscribeTo, List<String> unsubscribeFrom) {
		super();
		this.subscribeTo = subscribeTo;
		this.unsubscribeFrom = unsubscribeFrom;
	}

	private List<String> subscribeTo;
	
	private List<String> unsubscribeFrom;

	public List<String> getSubscribeTo() {
		return subscribeTo;
	}

	public void setSubscribeTo(List<String> subscribeTo) {
		this.subscribeTo = subscribeTo;
	}

	public List<String> getUnsubscribeFrom() {
		return unsubscribeFrom;
	}

	public void setUnsubscribeFrom(List<String> unsubscribeFrom) {
		this.unsubscribeFrom = unsubscribeFrom;
	}

}
