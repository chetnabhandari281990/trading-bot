package com.trading.bot;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.trading.bot.request.StockAlertsRequest;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {
	

	  	@Autowired
	    private MockMvc mockMvc;

	    @Test
	    public void shouldReturnBadRequestForEmptyAlert() throws Exception {
	    	StockAlertsRequest sa = new StockAlertsRequest();
	    	mockMvc.perform(post("/v1/alerts").contentType(MediaType.APPLICATION_JSON).content(sa.toString()))
	    	            .andExpect(status().isBadRequest());
	    }
	    
	    @Test
	    public void shouldReturnBadRequestWithoutProductId() throws Exception {
	    	StockAlertsRequest sa = new StockAlertsRequest();
	    	sa.setBuyPrice(new BigDecimal("2000"));
	    	sa.setLowerPrice(new BigDecimal("1990"));
	    	sa.setUpperPrice(new BigDecimal("2001"));
	    	mockMvc.perform(post("/v1/alerts").contentType(MediaType.APPLICATION_JSON).content(sa.toString()))
	    	            .andExpect(status().isBadRequest());
	    }
	    
	    @Test
	    public void shouldReturnOk() throws Exception {
	    	StockAlertsRequest sa = new StockAlertsRequest();
	    	sa.setProductId("xxx");
	    	sa.setBuyPrice(new BigDecimal("2000"));
	    	sa.setLowerPrice(new BigDecimal("1990"));
	    	sa.setUpperPrice(new BigDecimal("2001"));
	    	mockMvc.perform(post("/v1/alerts").contentType(MediaType.APPLICATION_JSON).content(sa.toString()))
	    	            .andExpect(status().isOk());
	    }
	    
	    

}