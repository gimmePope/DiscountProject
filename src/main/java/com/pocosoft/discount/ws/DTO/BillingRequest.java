package com.pocosoft.discount.ws.DTO;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BillingRequest {
	@NotBlank (message="Please supply an inquiry reference")
	private String enquiryRef;
	@NotBlank(message="Please supply a valid item reference")
	private String itemRef;
	private String customerNumber;
	@Min(value=1, message="Quantity cannot be less than 1")
	private int quantity = 1;

}
