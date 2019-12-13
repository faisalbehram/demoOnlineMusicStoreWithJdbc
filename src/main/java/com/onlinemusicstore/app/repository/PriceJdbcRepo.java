package com.onlinemusicstore.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Price2;
import com.onlinemusicstore.app.models.Product;

@Repository
public class PriceJdbcRepo {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void savePrice(Price2 price) {
		
//		System.out.println("the saving price is "   + price.getPercentageDiscount() + "//" + price.getFromDate() + "//" + price.getToDate()
//			
//				+ "//" + price.getPrice() + "//" + price.getPriceType() + "// " + price.getProduct());
		
		String sql = "INSERT  INTO  price2 (from_date,percentage_discount,price,price_type,to_date,product_id)"
				+ "values(:fromDate,:percentageDiscount,:price,:priceType,:toDate,:productId)";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("fromDate", price.getFromDate());
		parameters.addValue("percentageDiscount", price.getPercentageDiscount());
		parameters.addValue("price", price.getPrice());
		parameters.addValue("priceType", price.getPriceType());
		parameters.addValue("toDate", price.getToDate());
		parameters.addValue("productId", price.getProduct().getProductId());
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		namedParameterJdbcTemplate.update(sql, parameters);
		System.out.println("price are saved");
		
	}
	
	// return a list 
	public List<Price2> findPriceByProductId(String productId) {
		
		String sql = "select * from price2 where product_id = :productId";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("productId", productId);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		List<Price2> pricesList =namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

			@Override
			public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				Price2 price = new Price2();
				price.setId(rs.getInt("id"));;
				price.setPrice(rs.getDouble("price"));
				price.setPercentageDiscount(rs.getDouble("percentage_discount"));
				price.setPriceType(rs.getString("price_type"));
				price.setFromDate(rs.getDate("from_date"));
				price.setToDate(rs.getDate("to_date"));
				
				Product pro = new Product();
				pro.setProductId(rs.getString("product_id"));
				price.setProduct(pro);
				
				System.out.println("the product id in jdbc is " + price.getProduct().getProductId());
				return price;
			}
		});
		return pricesList;
		
	}
	
	// its optional
public Price2 findPriceByProductIdOptiona(String productId) {
		
		String sql = "select * from price2 where product_id = :productId";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("productId", productId);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		Price2 pricesList = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

			@Override
			public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				Price2 price = new Price2();
				price.setId(rs.getInt("id"));;
				price.setPrice(rs.getDouble("price"));
				price.setPercentageDiscount(rs.getDouble("percentage_discount"));
				price.setPriceType(rs.getString("price_type"));
				price.setFromDate(rs.getDate("from_date"));
				price.setToDate(rs.getDate("to_date"));
				
				Product pro = new Product();
				pro.setProductId(rs.getString("product_id"));
				price.setProduct(pro);
				
				System.out.println("the product it in jdbc is " + price.getProduct().getProductId());
				return price;
			}
		}));
		
		return pricesList;
		
	}
	
	
public List<Price2> allBasePrice() {
		
		String sql = "select * from price2 where price_type = :basePrice";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("basePrice", "Basic");
		System.out.println("in all base price");
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		
		List<Price2> pricesList =(List<Price2>) namedParameterJdbcTemplate.query(sql, parameters, new RowMapper<Price2>() {

			@Override
			public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				Price2 price = new Price2();
				price.setId(rs.getInt("id"));;
				price.setPrice(rs.getDouble("price"));
				price.setPercentageDiscount(rs.getDouble("percentage_discount"));
				price.setPriceType(rs.getString("price_type"));
				price.setFromDate(rs.getDate("from_date"));
				price.setToDate(rs.getDate("to_date"));
				
				Product pro = new Product();
				pro.setProductId(rs.getString("product_id"));
				price.setProduct(pro);
				
				System.out.println("the product it in jdbc is " + price.getProduct().getProductId());
				return price;
			}
		});
		return pricesList;
		
	}
	
// this for generic discount
public Price2 findGenericPrice() {
	
	String sql = "select * from price2 where price_type = :discount AND from_date IS NOT NULL AND product_id IS null";
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("discount", "Discount");
	System.out.println("in all base price");
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	
	Price2 pricesList = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

		@Override
		public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
			Price2 price = new Price2();
			price.setId(rs.getInt("id"));;
			price.setPrice(rs.getDouble("price"));
			price.setPercentageDiscount(rs.getDouble("percentage_discount"));
			price.setPriceType(rs.getString("price_type"));
			price.setFromDate(rs.getDate("from_date"));
			price.setToDate(rs.getDate("to_date"));
			
			Product pro = new Product();
			pro.setProductId(rs.getString("product_id"));
			price.setProduct(pro);
			
			System.out.println("the product it in jdbc is " + price.getProduct().getProductId());
			return price;
		}
	}));
	return pricesList;
	
}

// price of base price by product Id
public Price2 findByProductGetBasePrice(String productId) {
	
	String sql = "select * from price2 where product_id = :productId AND from_date IS NOT null AND price_type = 'Basic' ";
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("productId", productId);
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	
	Price2 pricesList = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

		@Override
		public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
			Price2 price = new Price2();
			price.setId(rs.getInt("id"));;
			price.setPrice(rs.getDouble("price"));
			price.setPercentageDiscount(rs.getDouble("percentage_discount"));
			price.setPriceType(rs.getString("price_type"));
			price.setFromDate(rs.getDate("from_date"));
			price.setToDate(rs.getDate("to_date"));
			
			Product pro = new Product();
			pro.setProductId(rs.getString("product_id"));
			price.setProduct(pro);
			
			System.out.println("the product it in jdbc is " + price.getProduct().getProductId());
			return price;
		}
	}));
	
	return pricesList;
	
	
}

// list price by id
public Price2 findByPriceId(int  priceId) {
	String sql = "select * from price2 where id = :priceId  ";
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("priceId", priceId);
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	
	Price2 pricesList = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

		@Override
		public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
			Price2 price = new Price2();
			price.setId(rs.getInt("id"));;
			price.setPrice(rs.getDouble("price"));
			price.setPercentageDiscount(rs.getDouble("percentage_discount"));
			price.setPriceType(rs.getString("price_type"));
			price.setFromDate(rs.getDate("from_date"));
			price.setToDate(rs.getDate("to_date"));
			
			Product pro = new Product();
			pro.setProductId(rs.getString("product_id"));
			price.setProduct(pro);
			
			System.out.println("the product it in jdbc is " + price.getProduct().getProductId());
			return price;
		}
	}));
	
	return pricesList;
	
	
}

public List<Price2> findByProductForViewProductDiscount(String  productId) throws IncorrectResultSetColumnCountException
 {
	String sql = "select * from price2 where product_id = :productId AND price_type = :discount AND from_date IS NOT null  ";
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("productId", productId);
	parameters.addValue("discount", "Discount");
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	List<Price2> pricesList = namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

		@Override
		public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
			Price2 price = new Price2();
			price.setId(rs.getInt("id"));;
			price.setPrice(rs.getDouble("price"));
			price.setPercentageDiscount(rs.getDouble("percentage_discount"));
			price.setPriceType(rs.getString("price_type"));
			price.setFromDate(rs.getDate("from_date"));
			price.setToDate(rs.getDate("to_date"));
			
			Product pro = new Product();
			pro.setProductId(rs.getString("product_id"));
			price.setProduct(pro);
			
			System.out.println("the product it in jdbc is " + price.getProduct().getProductId());
			return price;
		}
	});
	return pricesList;
	
	
}


public List<Price2> allDiscountPrice() {
	
	String sql = "select * from price2 where price_type = :discount AND product_id IS NOT null AND from_date IS NOT null";
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("discount", "Discount");
	System.out.println("in all base price");
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	
	
	List<Price2> pricesList =(List<Price2>) namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

		@Override
		public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
			Price2 price = new Price2();
			price.setId(rs.getInt("id"));;
			price.setPrice(rs.getDouble("price"));
			price.setPercentageDiscount(rs.getDouble("percentage_discount"));
			price.setPriceType(rs.getString("price_type"));
			price.setFromDate(rs.getDate("from_date"));
			price.setToDate(rs.getDate("to_date"));
			
			Product pro = new Product();
			pro.setProductId(rs.getString("product_id"));
			price.setProduct(pro);
			
			System.out.println("the product it in jdbc is " + price.getProduct().getProductId());
			return price;
		}
	});
	
	
	
	return pricesList;
	
}


// find price for update related to product Id 

	public List<Price2> findProductPriceForUpdate(String productId) {
			
			String sql = "select * from price2 where product_id = :productId AND price_type =:discount";
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("productId", productId);
			parameters.addValue("discount", "Discount");
			System.out.println("in all findProductPriceForUpdate price");
	
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	
	
	List<Price2> pricesList =(List<Price2>) namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Price2>() {

		@Override
		public Price2 mapRow(ResultSet rs, int rowNum) throws SQLException {
			Price2 price = new Price2();
			price.setId(rs.getInt("id"));;
			price.setPrice(rs.getDouble("price"));
			price.setPercentageDiscount(rs.getDouble("percentage_discount"));
			price.setPriceType(rs.getString("price_type"));
			price.setFromDate(rs.getDate("from_date"));
			price.setToDate(rs.getDate("to_date"));
			
			Product pro = new Product();
			pro.setProductId(rs.getString("product_id"));
			price.setProduct(pro);
			
			System.out.println("the product it in jdbc is " + price.getProduct().getProductId());
			return price;
		}
	});
	
	return pricesList;
	
}



}
