package com.onlinemusicstore.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

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
public class CardJdbcRepo {
	
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public Cart savingCart(Cart cart) {
		StringBuilder sql = new StringBuilder();
		KeyHolder holder = new GeneratedKeyHolder();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		
			System.out.println("in saving cart");
			sql.append("insert into cart (grand_total, customer_id) ");
			sql.append("values (:grand_total, :customer_id)");
				
			parameters.addValue("grand_total", cart.getGrandTotal());
			parameters.addValue("customer_id", cart.getCustomer().getCustomerId());

		
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
			
			namedParameterJdbcTemplate.update(sql.toString(), parameters,holder);
			cart.setCartId(holder.getKey().intValue());
			return cart;
	}
	
	// when  ever items added by the same customer into it will update the cart..
	public void updateingCart(Cart cart) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
			System.out.println("in updating cart");
			 sql.append("update cart set cart_id = :cart_id , grand_total = :grand_total, customer_id = :customer_id  where cart_id = :cart_id");
			 parameters.addValue("cart_id", cart.getCartId());
	
			 parameters.addValue("grand_total", cart.getGrandTotal());
			 parameters.addValue("customer_id", cart.getCustomer().getCustomerId());
		
				
			 NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
				
			 namedParameterJdbcTemplate.update(sql.toString(), parameters);
		
	}


	// it will just find the cart. if present or not
	public Cart findById(int cartId) {
		String sql = "select * from cart where cart_id = :cartId";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("cartId", cartId);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		Cart cart = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Cart>() {

			@Override
			public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
				Cart cart = new Cart();
				cart.setCartId(rs.getInt("cart_id"));
				cart.setGrandTotal(rs.getDouble("grand_total"));
				Customer customer = new Customer();
			
				customer.setCustomerId(rs.getInt("customer_id"));
				cart.setCustomer(customer);
				return cart;
			}
		}));
		
		return cart;
	}
	
	// this will be done checkout the cart to order completion
	public Cart findByCartIdForCheckOut(int cartId) {
		String sql = "select * from cart inner join customer  on cart.customer_id = customer.customer_id inner join shipping_address on shipping_address.customer_id = customer.customer_id where cart_id = :cartId AND address_type = 'billing'";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("cartId", cartId);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		Cart cart = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Cart>() {

			@Override
			public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
				Cart cart = new Cart();
				cart.setCartId(rs.getInt("cart_id"));
				cart.setGrandTotal(rs.getDouble("grand_total"));
				Customer customer = new Customer();
			
				customer.setCustomerId(rs.getInt("customer_id"));
				customer.setCustomerName(rs.getString("customer_name"));
				customer.setCustomerEmail(rs.getString("customer_email"));
				customer.setCustomerPhone(rs.getString("customer_phone"));
				
				ShippingAddress shippingAddress = new ShippingAddress();
				shippingAddress.setShippingAddressId(rs.getInt("shipping_address_id"));
				shippingAddress.setStreetName(rs.getString("street_name"));
				shippingAddress.setApartmentNumber(rs.getString("apartment_number"));
				shippingAddress.setCity(rs.getString("city"));
				shippingAddress.setCountry(rs.getString("country"));
				shippingAddress.setState(rs.getString("street_name"));
				shippingAddress.setZipCode(rs.getString("zip_code"));
				
				
				customer.setShippingAddress(shippingAddress);
				cart.setCustomer(customer);
				return cart;
			}
		}));
		
		return cart;
		
	}
	
	// it just take the cart for checkout and and take billing address from shipping table
	public Cart findByCartIdForCheckoutBillingAddress(int cartId) {
		String sql = "select * from cart inner join customer  on cart.customer_id = customer.customer_id inner join shipping_address on shipping_address.customer_id = customer.customer_id where cart_id = :cartId AND address_type = 'billing'";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("cartId", cartId);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		Cart cart = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Cart>() {

			@Override
			public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
				Cart cart = new Cart();
				cart.setCartId(rs.getInt("cart_id"));
				cart.setGrandTotal(rs.getDouble("grand_total"));
				Customer customer = new Customer();
			
				customer.setCustomerId(rs.getInt("customer_id"));
				customer.setCustomerName(rs.getString("customer_name"));
				customer.setCustomerEmail(rs.getString("customer_email"));
				customer.setCustomerPhone(rs.getString("customer_phone"));
				
				ShippingAddress shippingAddress = new ShippingAddress();
				shippingAddress.setStreetName(rs.getString("street_name"));
				shippingAddress.setApartmentNumber(rs.getString("apartment_number"));
				shippingAddress.setCity(rs.getString("city"));
				shippingAddress.setCountry(rs.getString("country"));
				shippingAddress.setState(rs.getString("street_name"));
				shippingAddress.setZipCode(rs.getString("zip_code"));
				
				
				customer.setShippingAddress(shippingAddress);
				cart.setCustomer(customer);
				return cart;
			}
		}));
		
		return cart;
		
	}

}

