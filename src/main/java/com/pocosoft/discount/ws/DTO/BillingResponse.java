package com.pocosoft.discount.ws.DTO;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class BillingResponse {
	
	private String responseCode;
	private String responseMessage;
	private double netPayable;
	private double percentageDiscount;
	private double hundredDiscount;
	private double totalDiscount;
	private double actualPrice;
	private List<String> appliedDiscounts;
	private String enquiryRef;
}
