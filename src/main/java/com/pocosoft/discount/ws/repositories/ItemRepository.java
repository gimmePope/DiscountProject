package com.pocosoft.discount.ws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pocosoft.discount.ws.entity.Item;

public interface ItemRepository extends JpaRepository<Item, String> {
	
	
	public Item findByItemRef(String ref);

}
