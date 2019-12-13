package com.onlinemusicstore.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinemusicstore.app.models.Product;
import com.onlinemusicstore.app.repository.ProductJdbcRepo;

@Service
public class ProductService {

	
	@Autowired
	private ProductJdbcRepo jdbcRepo;
	
	public List<Product> getAllPro(){
		System.out.println("getallpro");
		return jdbcRepo.allProducts();
	}
	
	public void saveProduct(Product product) {
		System.out.println("saving");
		jdbcRepo.save(product);
		System.out.println("saved");
	}
	
	public Product getProById(String id) {
		
		System.out.println("in productById is id + " + id );
	Product pro = jdbcRepo.getProductById(id);
	System.out.println("in productById is + " + pro );
	return pro;
	}
	
	public List<Product> getProductByName(String productName) {
		
		System.out.println("in productById is id + " + productName );
	List<Product> pro = jdbcRepo.findProductByName(productName);
	System.out.println("in productById is + " + pro );
	return pro;
	}
	
	public void deleteProduct(String id) {
		jdbcRepo.deleteProduct(id);
	}

	public void editProduct(Product product) {
		System.out.println("edit is saving");
		jdbcRepo.save(product);
		System.out.println("edit is saved");
		
	}

	
}
