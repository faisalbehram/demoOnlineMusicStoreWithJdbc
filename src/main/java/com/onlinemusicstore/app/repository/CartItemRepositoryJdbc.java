package com.onlinemusicstore.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Cart;
import com.onlinemusicstore.app.models.CartItem;
import com.onlinemusicstore.app.models.Price2;
import com.onlinemusicstore.app.models.Product;

@Repository
public class CartItemRepositoryJdbc {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	// find out all cart items in cart_items table
	public List<CartItem>findByCart(int cartId , Cart cart){
		
		String sql2 = "select * from cart_item inner join cart on cart.cart_id = cart_item.cart_id  AND cart.customer_id = :customerId inner join product on cart_item.product_id = product.product_id inner join price2 on price2.price_type = 'Basic' AND cart_item.product_id = price2.product_id";
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("cartId", cartId);
		parameters.addValue("customerId", cart.getCustomer().getCustomerId());
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		List<CartItem> cartItems =namedParameterJdbcTemplate.query(sql2, parameters,new RowMapper<CartItem>() {

			@Override
			public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {

				CartItem cartItem = new CartItem();
				cartItem.setCartItemId(rs.getInt("cart_item_id"));
				cartItem.setQuantity(rs.getInt("quantity"));
				cartItem.setTotalPrice(rs.getDouble("total_price"));
				Product product = new Product();
				
				Price2 price = new Price2();
				List<Price2> price2 = new ArrayList<Price2>() ;
				price.setPrice(rs.getDouble("price"));
				price2.add(price);		
				
				product.setProductId(rs.getString("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setPrice2(price2);
				
				cartItem.setProduct(product);
				
				Cart cart = new Cart();
				cart.setGrandTotal(rs.getDouble("grand_total"));
				cartItem.setCart(cart);

				return cartItem;
			}

		});

		return cartItems;
	
		
	}

	// it will take cart_item id from cart_item  table
	public CartItem findByCartItemId(int id) {
		String sql = "select * from cart_item,cart,product,price2 where cart_item.cart_id = cart.cart_id AND  cart_item.product_id = product.product_id AND product.product_id = price2.product_id AND price2.price_type = 'Basic' AND cart_item_id =:id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		CartItem cartItem = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<CartItem>() {

			@Override
			public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {

				CartItem cartItem = new CartItem();
				cartItem.setCartItemId(rs.getInt("cart_item_id"));
				cartItem.setQuantity(rs.getInt("quantity"));
				cartItem.setTotalPrice(rs.getDouble("total_price"));
				Product product = new Product();
				
				Price2 price = new Price2();
				List<Price2> price2 = new ArrayList<Price2>() ;
				price.setPrice(rs.getDouble("price"));
				price2.add(price);		
				
				product.setProductId(rs.getString("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setPrice2(price2);
				
				cartItem.setProduct(product);
				
				Cart cart = new Cart();
				cart.setCartId(rs.getInt("cart_id"));
				cart.setGrandTotal(rs.getDouble("grand_total"));
				cartItem.setCart(cart);
				
				// TODO Auto-generated method stub
				return cartItem;
			}

		}));
		
		return cartItem;
		
		
	}
	
	@Transactional
	@Modifying
	public void deleteCartItem(int cartItemId) {
		
		String sql = "delete  from cart_item where cart_item_id =  :id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", cartItemId);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		namedParameterJdbcTemplate.update(sql, parameters);

		
	}
	
	@Transactional
	@Modifying
	public void deleteCartItemByCartId(int cartId) {

		String sql = "delete   from cart_item where cart_id =  :id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		System.out.println("removing");
		parameters.addValue("id", cartId);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		namedParameterJdbcTemplate.update(sql, parameters);

		
	}

	public void save(CartItem cartItem) {
		StringBuilder insertSql  = new StringBuilder("insert into cart_item (quantity, total_price, cart_id, product_id)");
		insertSql.append("value (:quantity, :totalPrice, :cartId, :productId)");
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("quantity", cartItem.getQuantity());
		parameters.addValue("totalPrice", cartItem.getTotalPrice());
		parameters.addValue("cartId", cartItem.getCart().getCartId());
		parameters.addValue("productId", cartItem.getProduct().getProductId());
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		namedParameterJdbcTemplate.update(insertSql.toString(), parameters);
		
		
	}

	
	public List<CartItem> findAll() {
		String sql = "select * from cart_item";
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		List<CartItem> cartItems =namedParameterJdbcTemplate.query(sql,new RowMapper<CartItem>() {

			@Override
			public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {

				CartItem cartItem = new CartItem();
				cartItem.setCartItemId(rs.getInt("cart_item_id"));
				cartItem.setQuantity(rs.getInt("quantity"));
				cartItem.setTotalPrice(rs.getDouble("total_price"));
				Product product = new Product();
				
				Price2 price = new Price2();
				List<Price2> price2 = new ArrayList<Price2>() ;
				price.setPrice(rs.getDouble("price"));
				price2.add(price);		
				
				product.setProductId(rs.getString("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setPrice2(price2);
				
				cartItem.setProduct(product);
				
				Cart cart = new Cart();
				cart.setGrandTotal(rs.getDouble("grand_total"));
				cartItem.setCart(cart);
				
				// TODO Auto-generated method stub
				return cartItem;
			}

		});

		return cartItems;
	}
}
