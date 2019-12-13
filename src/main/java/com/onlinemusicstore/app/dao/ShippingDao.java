package com.onlinemusicstore.app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.ShippingAddress;
import com.onlinemusicstore.app.repository.ShippingJdbcRepo;

@Repository
public class ShippingDao {
	
//	@Autowired
//	private ShippingRepository shippingRepository;
	
	@Autowired
	private ShippingJdbcRepo shippingJdbcRepo;
	
	public ShippingAddress saveShipping(ShippingAddress address,ShippingAddress shippingAddress) {
		
		System.out.println("the shiping are saved + " );
		
		shippingAddress.setCustomer(shippingAddress.getCustomer());
		
		shippingAddress.setCustomer(address.getCustomer());
		shippingAddress.setAddressType(address.getAddressType());
		
		System.out.println("the shiping are saved" + shippingAddress.getCustomer());
		System.out.println("the shiping are saved" + shippingAddress.getApartmentNumber());

		System.out.println("the shiping are saved" + shippingAddress.getCustomer().getCustomerId());
		
		ShippingAddress id = shippingJdbcRepo.savingShipping(shippingAddress);
	
		return id;
	}

}
