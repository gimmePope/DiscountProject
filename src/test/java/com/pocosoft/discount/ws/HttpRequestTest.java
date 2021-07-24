package com.pocosoft.discount.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.pocosoft.discount.ws.DTO.BillingRequest;
import com.pocosoft.discount.ws.DTO.BillingResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate httpClient;
	
	@Test
	public void assertResponseValidResponseForInvalidItemRef()
	{
		BillingRequest req = new BillingRequest();
		 
		req.setEnquiryRef("354672282");
		req.setItemRef("ASSSSSS23145");
		req.setQuantity(1);
		req.setCustomerNumber("0223");
		String url = "http://localhost:" + port + "/api/retail/store/order/discount/calculation";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Content-Type","application/json");
		headers.add("Accept", "application/json");
		 
		 JSONObject payload = new JSONObject(req);
		 
		 HttpEntity<String> entity = new HttpEntity<String>(payload.toString(),headers);
		
		 ResponseEntity<BillingResponse> resp = httpClient.exchange(url, HttpMethod.POST, entity, BillingResponse.class, new Object[0]);
				
		
		assertEquals((resp.getStatusCodeValue() == 200)&&(resp.getBody() != null), true);
		
	}
	
	@Test
	public void assertCorrectnessOfAppliedDiscountRules()
	{
		BillingRequest req = new BillingRequest();
		 
		req.setEnquiryRef("354672282");
		req.setItemRef("SA777");
		req.setQuantity(1);
		req.setCustomerNumber("0223");
		String url = "http://localhost:" + port + "/api/retail/store/order/discount/calculation";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Content-Type","application/json");
		headers.add("Accept", "application/json");
		 
		 JSONObject payload = new JSONObject(req);
		 
		 HttpEntity<String> entity = new HttpEntity<String>(payload.toString(),headers);
		
		 ResponseEntity<BillingResponse> resp = httpClient.exchange(url, HttpMethod.POST, entity, BillingResponse.class, new Object[0]);
		 int appliedRules = resp.getBody().getAppliedDiscounts().size();
		 boolean conditions = (resp.getStatusCodeValue() == 200) && (appliedRules == 0);
		
		assertTrue(conditions);
	}


}
