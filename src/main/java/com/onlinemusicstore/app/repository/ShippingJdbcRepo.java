package com.onlinemusicstore.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.ShippingAddress;

@Repository
public class ShippingJdbcRepo  {

	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public ShippingAddress savingShipping(ShippingAddress shippingAddress) {
		KeyHolder holder = new GeneratedKeyHolder();
		StringBuilder sql = new StringBuilder("insert into shipping_address (apartment_number, city, country, state, street_name, zip_code, customer_id, address_type)");
		sql.append( "values (:apartment_number, :city, :country, :state, :street_name, :zip_code, :customer_id, :address_type)");
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		parameters.addValue("apartment_number", shippingAddress.getApartmentNumber());
		parameters.addValue("city", shippingAddress.getCity());
		parameters.addValue("country", shippingAddress.getCountry());
		parameters.addValue("state", shippingAddress.getState());
		parameters.addValue("street_name", shippingAddress.getStreetName());
		parameters.addValue("zip_code", shippingAddress.getZipCode());
		parameters.addValue("customer_id", shippingAddress.getCustomer().getCustomerId());
		parameters.addValue("address_type", shippingAddress.getAddressType());
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	
		namedParameterJdbcTemplate.update(sql.toString(), parameters,holder);
		shippingAddress.setShippingAddressId(holder.getKey().intValue());
		
		return shippingAddress;
	}

}
