package com.trading.bot.config;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.trading.bot.utils.CommonUtils;

/**
 * Handles the response in case of an error. We don't throw an exception when 
 * there’s an HTTP error, as exception bubbles out RestTemplate call and we don't 
 * get the error object we want to read. So we just need to replace it with 
 * a custom handler that doesn’t throw the exception
 * 
 *
 */
public class RestResponseErrorHandler implements ResponseErrorHandler 
{
	/**
     * We don't throw exception, just log it and let ResponseExtractor do 
     * everything else
     */
    public void handleError(final ClientHttpResponse response) throws IOException 
    {
    	// no implementation
    }

    /**
     * Indicates whether the given response has any errors
     */
    public boolean hasError(final ClientHttpResponse response) throws IOException 
    {
        return CommonUtils.isError(response.getStatusCode());
    }
}