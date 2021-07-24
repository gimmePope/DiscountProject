package com.pocosoft.discount.ws;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.pocosoft.discount.ws.entity.Customer;
import com.pocosoft.discount.ws.entity.Item;
import com.pocosoft.discount.ws.repositories.CustomerRepository;
import com.pocosoft.discount.ws.repositories.ItemRepository;

@SpringBootApplication
public class DiscountServicePrjApplication {

	@Autowired
	ItemRepository itemRepo;
	@Autowired
	CustomerRepository custRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(DiscountServicePrjApplication.class, args);
	}
	@Bean
	CommandLineRunner runner()
	{
		return args ->
		{
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
			Date reg = sdf.parse("2017-02-24");
			
			if(custRepo.count() == 0)
			{
				//adding test customers
				Customer customer1 = new Customer("0012", "Adamu Kabir", "081333778899", "adamuk@testmail.com", false, true, new Timestamp((new Date()).getTime()));
				Customer customer2 = new Customer("0013", "James Green", "081333778777", "jamesgeo@testmail.com", true, false, new Timestamp((new Date()).getTime()));
				Customer customer3 = new Customer("0025", "Abel Johnson", "081333778222", "abelson@testmail.com", false, false, new Timestamp((new Date()).getTime()));
				Customer customer4 = new Customer("0028", "Gabriel Johnson", "081000778744", "gabbyj@gmail.com", false, false, new Timestamp(reg.getTime()));
				Customer customer5 = new Customer("0223", "Sam Stone", "081223778227", "stone@testmail.com", true, true, new Timestamp(reg.getTime()));
				List<Customer> customers = new ArrayList<Customer>();
				customers.add(customer1);
				customers.add(customer2);
				customers.add(customer3);
				customers.add(customer4);
				customers.add(customer5);
				custRepo.saveAll(customers);

			
			}
			
			if(itemRepo.count() == 0)
			{
				List<Item> items = new ArrayList<Item>();
				
				Item item1 = new Item("SA4312", "RUBBER BOOT", "", "WEATHER GEAR", 125.20);
				Item item2 = new Item("SA002", "RAIN COAT", "", "WEATHER GEAR", 35.00);
				Item item3 = new Item("SA777", "RED MOUNTAIN COFFEE", "", "GROCERIES", 18.50);
				Item item4 = new Item("SA532", "POTATO CHIP BAG", "", "GROCERIES", 260.50);
				Item item5 = new Item("EP117", "CHAIN SAW", "", "EQUIPMENT", 600.25);
				Item item6 = new Item("WE712", "PLAY STATION 5", "", "GAMES", 1080.50);
				Item item7 = new Item("SA700", "FANCY PEN", "", "OTHERS", 10.50);
				Item item8 = new Item("EE700", "FLASH DRIVE 500G", "", "COMPUTER", 23.10);
				items.add(item1);
				items.add(item2);
				items.add(item3);
				items.add(item4);
				items.add(item5);
				items.add(item6);
				items.add(item7);
				items.add(item8);
				itemRepo.saveAll(items);
			}
			
		};
	}

}
