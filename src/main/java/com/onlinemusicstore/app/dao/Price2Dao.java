package com.onlinemusicstore.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Price2;
import com.onlinemusicstore.app.repository.PriceJdbcRepo;


@Repository
public class Price2Dao {

//	@Autowired
//	private Price2Repository price2Repository;
	
	@Autowired
	private PriceJdbcRepo jdbcRepo;
	
	// to save the price
	public void savePrice(Price2 price2) {
//		Price2 newPrice = new Price2();
//		newPrice.setId(price2.getId());
//		newPrice.setPrice(price2.getPrice());
//		newPrice.setPercentageDiscount(price2.getPercentageDiscount());
//		newPrice.setPriceType(price2.getPriceType());
//		newPrice.setFromDate(price2.getFromDate());
//		newPrice.setToDate(price2.getToDate());
//		newPrice.setProduct(price2.getProduct());

		jdbcRepo.savePrice(price2);
	}
	
	public void save(Price2 price) {
		jdbcRepo.savePrice(price);
	}
	
	//return list of price2 by product id no constrant are there
	public List<Price2> findByProductId(String productId) {
	//	List<Price2> price = price2Repository.findByProductProductId(productId);
		List<Price2> price = jdbcRepo.findPriceByProductId(productId);
		System.out.println("the product id is from priceDao is " + price);
		return price;
	}
	
	// now find the product by id and get the basic discount to show to the user
	public List<Price2> findProductForViewProduct(String productId) {
//		Optional<Price2> price = price2Repository.findByProductForViewProductDiscount(productId);
//		System.out.println("the product id is from priceDao is " + price);
		
		List<Price2> checking = jdbcRepo.findByProductForViewProductDiscount(productId);
		
		return checking;
	}
	
	public Optional<Price2> findByPro(String productId) {
		
		//Optional<Price2> price = price2Repository.findProduct(productId);
		Price2 checking = jdbcRepo.findPriceByProductIdOptiona(productId);
		Optional<Price2> optionalPrice = Optional.of(checking);
		System.out.println("the product id is from priceDao is in findByPro  " + optionalPrice);
		return optionalPrice;
	}
	
	
	
	
//	public List<Price2> allPrice(){
//		return price2Repository.findAllPrices();
//	}
	
	// all basic prices
	public List<Price2> findAllBasicPrice(){
		

		
		return jdbcRepo.allBasePrice();
	}
	
	// all disocunt prices where product id is not null
	public List<Price2> findAllDiscountPrice()
	{
		return jdbcRepo.allDiscountPrice();
	}
	
	// it return all list of product where product id is not null 
	public List<Price2> findProductPriceForUpdate(String productId)
	{
		//return price2Repository.findProductPriceForUpdate(productId);
		return jdbcRepo.findProductPriceForUpdate(productId);
	}
	
	// all discount where product id is null
	
	
	

	public Optional<Price2> findById(int priceId) {
	//	Optional<Price2> price2 = price2Repository.findById(priceId);
		
		Price2 checking = jdbcRepo.findByPriceId(priceId);
		Optional<Price2> optionalPrice = Optional.of(checking);
		return optionalPrice;
	}

	
	public Optional<Price2> findProductBasicPrice(String productId) {
		
		Price2 checking = jdbcRepo.findByProductGetBasePrice(productId);
		Optional<Price2> optionalPrice = Optional.of(checking);
		System.out.println("checking is "  + checking);
		
		return optionalPrice;
	}
	
	public Optional<Price2> genericDiscount() {
		Price2 checking = jdbcRepo.findGenericPrice();
		Optional<Price2> optionalPrice = Optional.of(checking);
		return optionalPrice;
	}

	

	
	
//	public void leftJoin() {
//		List<Price2> price2 = price2Repository.findProduct();
//		
//	}
}
