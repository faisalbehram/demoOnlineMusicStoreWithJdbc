package com.onlinemusicstore.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Cart;
import com.onlinemusicstore.app.models.Customer;
import com.onlinemusicstore.app.models.ShippingAddress;

@Repository
public class CustomerJdbcRepo {
	
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	//finding out customer by email  and take all the all info regarding with that customer
	
	public Customer findByCustomerEmail(String email) {
		
		
		String sql = "select * from customer inner join cart on customer.customer_id = cart.customer_id where customer.customer_email = :customerEmail";
		
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("customerEmail", email);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		
		Customer customer = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Customer>() {

			@Override
			public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
				Customer customer = new Customer();
				
				customer.setCustomerId(rs.getInt("customer_id"));
				customer.setCustomerEmail(rs.getString("customer_email"));
				customer.setCustomerName(rs.getString("customer_name"));
				customer.setCustomerPhone(rs.getString("customer_phone"));
				customer.setEnabled(rs.getBoolean("enabled"));
				customer.setUsername(rs.getString("username"));
				
				
				
				Cart cart = new Cart();
				cart.setCartId(rs.getInt("cart_id"));
				customer.setCart(cart);			
				return customer;
			}
		}));

		return customer;
		
	}
	
// find customer by username	
public Customer findByCustomerUserName(String username) {
		
		String sql = "select * from customer where username = :username";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("username", username);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		
		Customer customer = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Customer>() {

			@Override
			public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
				Customer customer = new Customer();
				
				customer.setCustomerId(rs.getInt("customer_id"));
				customer.setCustomerEmail(rs.getString("customer_email"));
				customer.setCustomerName(rs.getString("customer_name"));
				customer.setCustomerPhone(rs.getString("customer_phone"));
				customer.setEnabled(rs.getBoolean("enabled"));
				customer.setUsername(rs.getString("username"));
				
			
				
				Cart cart = new Cart();
				cart.setCartId(rs.getInt("cart_id"));
				customer.setCart(cart);
				
				ShippingAddress shippingAddress = new ShippingAddress();
				shippingAddress.setShippingAddressId(rs.getInt("shipping_address_id"));
				customer.setShippingAddress(shippingAddress);
				
				
				return customer;
			}
		}));

		return customer;
		
	}

public Customer findById(int id) {
	
	String sql = "select * from customer where customer_id = :id";
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("id", id);
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	
	
	Customer customer = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Customer>() {

		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Customer customer = new Customer();
			
			customer.setCustomerId(rs.getInt("customer_id"));
			customer.setCustomerEmail(rs.getString("customer_email"));
			customer.setCustomerName(rs.getString("customer_name"));
			customer.setCustomerPhone(rs.getString("customer_phone"));
			customer.setEnabled(rs.getBoolean("enabled"));
			customer.setUsername(rs.getString("username"));
			
			
			Cart cart = new Cart();
			cart.setCartId(rs.getInt("cart_id"));
			customer.setCart(cart);
			
			ShippingAddress shippingAddress = new ShippingAddress();
			shippingAddress.setShippingAddressId(rs.getInt("shipping_address_id"));
			customer.setShippingAddress(shippingAddress);
			
			
			return customer;
		}
	}));

	return customer;
	
}
	
	public List<Customer> findAll(){
		
		 String sql = "SELECT * FROM CUSTOMER";

	        List<Customer> customers = jdbcTemplate.query(sql,new RowMapper<Customer>() {

				@Override
				public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
					Customer customer = new Customer();
					
					customer.setCustomerId(rs.getInt("customer_id"));
					customer.setCustomerEmail(rs.getString("customer_email"));
					customer.setCustomerName(rs.getString("customer_name"));
					customer.setCustomerPhone(rs.getString("customer_phone"));
					customer.setEnabled(rs.getBoolean("enabled"));
					customer.setUsername(rs.getString("username"));
					
					
					Cart cart = new Cart();
					cart.setCartId(rs.getInt("cart_id"));
					customer.setCart(cart);
					
					ShippingAddress shippingAddress = new ShippingAddress();
					shippingAddress.setShippingAddressId(rs.getInt("shipping_address_id"));
					customer.setShippingAddress(shippingAddress);
					
					
					return customer;
				}
			});

	        return customers;
	}
	
	
	
	
	public Customer saveCustomer(Customer customer) {
		KeyHolder holder = new GeneratedKeyHolder();
		String sql = "INSERT  INTO  customer (customer_email, customer_name, customer_phone, enabled, password, username) values(:customerEmail,:customerName,:customerPhone,:enabled,:password,:userName)";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		System.out.println("customer are saved1");
		parameters.addValue("customerEmail", customer.getCustomerEmail());
		System.out.println("customer are saved2");
		parameters.addValue("customerName", customer.getCustomerName());
		System.out.println("customer are saved3");
		parameters.addValue("customerPhone", customer.getCustomerPhone());
		parameters.addValue("enabled", customer.isEnabled());
		parameters.addValue("password", customer.getPassword());
		parameters.addValue("userName", customer.getUsername());
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		namedParameterJdbcTemplate.update(sql, parameters,holder);
		System.out.println("customer are saved");
		customer.setCustomerId(holder.getKey().intValue());
		return customer;
	}
	

	public void updateCustomer(int id, Customer customer) {
		String sqlUpdate = "UPDATE    customer set billing_address_id =:billing_address_id,cart_id = :cart_id, shipping_address_id = :shipping_address_id where customer_id = :customer_id ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
	
		parameters.addValue("cart_id", customer.getCart().getCartId());
		parameters.addValue("shipping_address_id", customer.getShippingAddress().getShippingAddressId());
		parameters.addValue("customer_id", id);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		namedParameterJdbcTemplate.update(sqlUpdate, parameters);
		
		System.out.println("updated ");
		
	}


}
