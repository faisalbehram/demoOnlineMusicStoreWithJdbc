package com.onlinemusicstore.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Product;

@Repository
public class ProductJdbcRepo {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<Product> allProducts() {
		System.out.println("in jdbc Template");
		List<Product> product = jdbcTemplate.query("select * from product ",new RowMapper<Product>() {

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				Product product = new Product();
				product.setProductId(rs.getString("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setProductCategory(rs.getString("product_category"));
				product.setProductCondition(rs.getString("product_condition"));
				product.setProductDescription(rs.getString("product_description"));
				product.setProductManufacturer(rs.getString("product_manufacturer"));
				product.setProductStatus(rs.getString("product_status"));
				product.setUnitInStock(rs.getInt("unit_in_stock"));
				return product;
			}
		});
		System.out.println("the all product are  "  + product);
		
		return product;
	}
	
	public Product getProductById(String Id) {
		
		Product product = jdbcTemplate.queryForObject("select * from product where product_id = ? ", new Object[] {Id}, new RowMapper<Product>() {

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				Product product = new Product();
				product.setProductId(rs.getString("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setProductCategory(rs.getString("product_category"));
				product.setProductCondition(rs.getString("product_condition"));
				product.setProductDescription(rs.getString("product_description"));
				product.setProductManufacturer(rs.getString("product_manufacturer"));
				product.setProductStatus(rs.getString("product_status"));
				product.setUnitInStock(rs.getInt("unit_in_stock"));
				return product;
			}
		});
		return product;
	}
	
	
	
	public List<Product> findProductByName(String productName) {
		String sql = "select * from product where product_name LIKE :productName ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("productName",productName+ "%");
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		
		List<Product> productList =namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Product>() {

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				Product product = new Product();
				product.setProductId(rs.getString("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setProductCategory(rs.getString("product_category"));
				product.setProductCondition(rs.getString("product_condition"));
				product.setProductDescription(rs.getString("product_description"));
				product.setProductManufacturer(rs.getString("product_manufacturer"));
				product.setProductStatus(rs.getString("product_status"));
				product.setUnitInStock(rs.getInt("unit_in_stock"));
				return product;
			}
		});
		return productList;
	}

	@Transactional
	@Modifying
	public void deleteProduct(String id) {
		String sql = "delete  from product where product_id =  :id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		namedParameterJdbcTemplate.update(sql, parameters);

		
	}
	
	public void save(Product product) {
		KeyHolder holder = new GeneratedKeyHolder();
		
		StringBuilder insertSql = new StringBuilder( "INSERT  INTO  product (product_id,product_category,product_condition,product_description,product_manufacturer,product_name,product_status,unit_in_stock)");
		insertSql.append( "values(:productId,:product_category,:product_condition,:product_description,:product_manufacturer,:product_name, :product_status, :unit_in_stock)");

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		parameters.addValue("productId", product.getProductId());
		parameters.addValue("product_category", product.getProductCategory());
		parameters.addValue("product_condition", product.getProductCondition());
		parameters.addValue("product_description", product.getProductDescription());
		parameters.addValue("product_manufacturer", product.getProductManufacturer());
		parameters.addValue("product_name", product.getProductName());
		parameters.addValue("product_status", product.getProductStatus());
		parameters.addValue("unit_in_stock", product.getUnitInStock());
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

		namedParameterJdbcTemplate.update(insertSql.toString(), parameters,holder);
		

		
	}
	
}
