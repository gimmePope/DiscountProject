package com.pocosoft.discount.ws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pocosoft.discount.ws.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

	
	public Customer findByCustomerNumber(String number);
}
