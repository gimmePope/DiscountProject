package com.pocosoft.discount.ws.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {
	
	@Id
	private String itemRef;
	private String itemName;
	private String itemDescription;
	private String itemCategory;
	private double price;

}
