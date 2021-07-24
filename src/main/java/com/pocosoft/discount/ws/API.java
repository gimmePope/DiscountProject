package com.pocosoft.discount.ws;

import java.net.NetPermission;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pocosoft.discount.ws.DTO.BillingRequest;
import com.pocosoft.discount.ws.DTO.BillingResponse;
import com.pocosoft.discount.ws.entity.Customer;
import com.pocosoft.discount.ws.entity.Item;
import com.pocosoft.discount.ws.repositories.CustomerRepository;
import com.pocosoft.discount.ws.repositories.ItemRepository;

@RestController
@RequestMapping("api")
public class API {
	
	@Autowired
	ItemRepository itemRepo;
	@Autowired
	CustomerRepository custRepo;
	
	@PostMapping("/retail/store/order/discount/calculation")
	public ResponseEntity<BillingResponse> calculateOrderPayable(@RequestBody @Valid BillingRequest request, Errors errors)
	{
		
		if(errors.hasErrors())
		{
			System.out.println("Error in request");
			BillingResponse response = new BillingResponse();
			List <FieldError> errs = errors.getFieldErrors();
			response.setResponseCode("99");
			response.setResponseMessage(errs.get(0).getCode());
			return new ResponseEntity<BillingResponse>(response, HttpStatus.OK);
		}
		Item orderItem = itemRepo.findByItemRef(request.getItemRef());
		
		if(orderItem == null)
		{
			BillingResponse response = new BillingResponse();
			
			response.setResponseCode("25");
			response.setResponseMessage("Item not found");
			response.setEnquiryRef(request.getEnquiryRef());
			return new ResponseEntity<BillingResponse>(response, HttpStatus.OK);
		}
		else
		{
			double actualPrice = orderItem.getPrice() * request.getQuantity();
			double totalDiscount = 0;
			double percentageDiscount = 0;
			double hundredDiscount = 0;
			List<String> appliedDiscounts = new ArrayList<String> ();
			BillingResponse response = new BillingResponse();
			//Checking if percentage discount applies
			
			if(!orderItem.getItemCategory().equalsIgnoreCase("groceries"))
			{
				if(request.getCustomerNumber() != null && !request.getCustomerNumber().trim().equals(""))
				{
					// customerNumber supplied
					
					//lets find the customer to be billed
					
					Customer customer = custRepo.findByCustomerNumber(request.getCustomerNumber());
					
					if(customer != null)
					{
						System.out.println("customer record located");
						// customer record located
						
						//get customer registration date
						
						Timestamp dateOfReg = customer.getCustomerCreationDate();
						
						LocalDate start = dateOfReg.toLocalDateTime().toLocalDate();
						LocalDate end= (LocalDateTime.now()).toLocalDate();
						int years = (Period.between(start, end)).getYears();
						 
						
						
						if(customer.isStaff())
						{
							System.out.println("Customer is a staff of the store");
							percentageDiscount =  (actualPrice * 30)/100;
							appliedDiscounts.add("staff discount");
						}
						else if (customer.isAffliate())
						{
							System.out.println("Customer is an affliate of the store");
							percentageDiscount = (actualPrice * 10)/100;
							appliedDiscounts.add("Affiliate discount");
						}
						else if(years > 1)
						{
							System.out.println("Customer is  2 plus");
							percentageDiscount = (actualPrice * 5)/100;
							appliedDiscounts.add("2years customer incentive");
						}
					}
					
					
				}//customer check
				
			}
			
			hundredDiscount = (int)(actualPrice / 100) * 5;
			if(hundredDiscount != 0)
			{
				appliedDiscounts.add("Discount on each Hundred Dollars");
			}
		//	System.out.println("Percentage Discount: " + percentageDiscount);
			totalDiscount = hundredDiscount + percentageDiscount;
			double netPayable = actualPrice - totalDiscount;
			
			response.setResponseCode("00");
			response.setAppliedDiscounts(appliedDiscounts);
			response.setHundredDiscount(hundredDiscount);
			response.setNetPayable(netPayable);
			response.setActualPrice(actualPrice);
			response.setPercentageDiscount(percentageDiscount);
			response.setTotalDiscount(totalDiscount);
			response.setResponseMessage("Processed");
			response.setEnquiryRef(request.getEnquiryRef());
			return new ResponseEntity<BillingResponse>(response,HttpStatus.OK);
		}
		
		
	}

}
