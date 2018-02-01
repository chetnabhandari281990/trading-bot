package com.trading.bot.exception;

public class TradingBotRunTimeException extends RuntimeException {
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 7066906597051384792L;
    private String code;
    private String msg;
    /**
     * 
     * @param code
     * @param msg
     */
    public TradingBotRunTimeException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
    /**
     * 
     * @param code
     */
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
}
