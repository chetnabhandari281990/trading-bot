package com.trading.bot.utils;

import java.math.BigDecimal;

public class AppConstants {
	
	public static final String DIRECTION = "BUY";
	public static final String CURRENCY = "BUX";
	
	public static final BigDecimal DEFAULT_INVESTING_AMMOUNT = new BigDecimal(20.00);
	public static final int LEVERAGE = 1;
	
	public static boolean ACTIVE = true;
	
	public static boolean INACTIVE = false;
	
	
	
	public static final String AUTHORIZATION = "Authorization";
	
	
	
	public static final String ACCEPT_LANGUAGE = "nl-NL,en;q=0.8";
	
	public static final String TRADING_QUOTE = "trading.quote";
	public static final String CONNECTED = "connect.connected";
	
	public static final String TRADING_PREFIX = "trading.product.";
	
	
	public static final class ACTION_TO_TAKE {
		public static final String BUY = "BUY";
		public static final String CLOSE = "CLOSE";
	}

}
