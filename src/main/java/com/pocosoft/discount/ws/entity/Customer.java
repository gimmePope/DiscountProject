package com.pocosoft.discount.ws.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Customer {
	
	@Id
	private String customerNumber;
	private String customerFullName;
	private String customerPhoneNumber;
	private String customerEmail;
	private boolean isStaff;
	private boolean isAffliate;
	private Timestamp customerCreationDate;

}
