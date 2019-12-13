package com.onlinemusicstore.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Cart;
import com.onlinemusicstore.app.models.Customer;
import com.onlinemusicstore.app.models.CustomerOrder;
import com.onlinemusicstore.app.models.ShippingAddress;

// for checkout the customer order this class is used
@Repository
public class CustomerOrderRepoJdbc {
	
	@Autowired
	JdbcTemplate jdbcTemplate;


	
	public List<CustomerOrder> customerOrderfindAll(){
		
		String sql = "select * from customer_order";

		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		
		List<CustomerOrder> customerOrder = namedParameterJdbcTemplate.query(sql, new RowMapper<CustomerOrder>() {

			@Override
			public CustomerOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerOrder customerOrder  = new CustomerOrder();
				
		
				Cart cart = new Cart();
				Customer customer = new Customer();
				ShippingAddress shippingAddress = new ShippingAddress();
				
	
				cart.setCartId(rs.getInt("cart_id"));
				customer.setCustomerId(rs.getInt("customer_id"));
				shippingAddress.setShippingAddressId(rs.getInt("shipping_address_id"));
				

				customerOrder.setCart(cart);
				customerOrder.setShippingAddress(shippingAddress);
				customerOrder.setCustomer(customer);
				
				
				return customerOrder;
			}
		});
		return customerOrder;
		
		
	}

	public void save(CustomerOrder customerOrder) {
		String insertSql = "insert into customer_order ( cart_id, customer_id, shipping_address_id) "
				+ "values ( :cartId, :customerId, :shippingAddressId)";
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("cartId", customerOrder.getCart().getCartId());
		parameters.addValue("customerId", customerOrder.getCustomer().getCustomerId());
		parameters.addValue("shippingAddressId", customerOrder.getShippingAddress().getShippingAddressId());
		
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		namedParameterJdbcTemplate.update(insertSql, parameters);
		System.out.println("done");
		
	}
}
