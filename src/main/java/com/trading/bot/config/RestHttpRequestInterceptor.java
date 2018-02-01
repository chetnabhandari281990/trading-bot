package com.trading.bot.config;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * Interceptor class for all REST based requests being generated from system using 
 * RestTemplate bean created in RestTemplateConfig.java
 * 
 *
 */
public class RestHttpRequestInterceptor implements ClientHttpRequestInterceptor 
{
	/**
	 * Intercepts the request before sending it to Remote server
	 */
    public ClientHttpResponse intercept(final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution)
            throws IOException 
    {
    	// E.g. Any header can be added to request //
        // HttpHeaders headers = request.getHeaders();
        // headers.add("X-User-Agent", "Groupon-India");
        return execution.execute(request, body);
    }
}