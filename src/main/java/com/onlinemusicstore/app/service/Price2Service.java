package com.onlinemusicstore.app.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinemusicstore.app.dao.Price2Dao;
import com.onlinemusicstore.app.models.Price2;
import com.onlinemusicstore.app.models.PricesWithDiscount;
import com.onlinemusicstore.app.models.Product;


@Service
public class Price2Service {
	
	@Autowired
	private Price2Dao price2Dao;
	
	public List<Price2> findbyProductId(String productId) {
		System.out.println("the product id in priceservice is " + productId);
		List<Price2> price = price2Dao.findByProductId(productId);
		return price;
	}
	
	public void saveThePrice(Price2 price2) {
				
		if(price2.getPercentageDiscount() == 0) {
			price2.setPriceType("Basic");
			price2.setToDate(null);
			System.out.println("in priceservice the percentage discount is " + price2.getPercentageDiscount());
			try {
				price2.setFromDate(dateSaving());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			price2Dao.savePrice(price2);
		}
		else 
		{
			price2.setPriceType("Discount");
			price2.setToDate(null);
			try {
				price2.setFromDate(dateSaving());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			price2Dao.savePrice(price2);
			
		}
	}
	
	
	public PricesWithDiscount getDiscountById(String productId) {
		List<Price2> price2 = price2Dao.findProductForViewProduct(productId);
		System.out.println("what is price is " + price2);
		Optional<Price2> getBasePrice = getBasicPriceForViewProduct(productId);
		
		PricesWithDiscount pricesWithDiscount = new PricesWithDiscount();
		if(price2.isEmpty()) {
			pricesWithDiscount.setDiscountPercentage(0);
			pricesWithDiscount.setDiscountPrice(0);
			pricesWithDiscount.setBasicPrice(getBasePrice.get().getPrice());
			return pricesWithDiscount;
		}
		else 
		{
			
		double discount = ( price2.get(0)).getPercentageDiscount()/100;
		
		double basePrice = getBasePrice.get().getPrice();
		
		double discountPrice = basePrice - ( basePrice * discount);
		pricesWithDiscount.setDiscountPercentage(discount * 100);
		pricesWithDiscount.setDiscountPrice(discountPrice);
		
		pricesWithDiscount.setBasicPrice(basePrice);
		
		return pricesWithDiscount;
		}
	}
	
	public Optional<Price2> getBasicPriceForViewProduct(String productId) {
		return price2Dao.findProductBasicPrice(productId);	
	}
	
	public List<Price2> findAllBasicPrice(){
		List<Price2> allPrice = price2Dao.findAllBasicPrice();
		
		System.out.println("the price list in price2service base price  + " + allPrice.size());
		return allPrice;
		
	}
	
	public List<Price2> findAllDiscountPrice(){
		List<Price2> allPrice = price2Dao.findAllDiscountPrice();
		System.out.println("the price list in price2service all discount is  + " + allPrice.size());
		return allPrice;
		
	}
	
	
	
	public Date dateSaving() throws ParseException 
		{  
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date date = new Date();
	        System.out.println("current: " +parseFormat.parse(dateFormat.format(date)));
            return parseFormat.parse(dateFormat.format(date));    
	}

	public Optional<Price2> findById(int priceId) {
		
		return price2Dao.findById(priceId);
	}

	public void disableThePrice(Price2 price2) {
		
		price2.setFromDate(null);
		try {
			price2.setToDate(dateSaving());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		price2Dao.savePrice(price2);
			
	}
	
	
	//new pricing is started here
	


	public Optional<Price2> findByPro(String productId) {
		return price2Dao.findByPro(productId);
		
	}
	
	
	
	public void saveGenericThePrice(double discount) {
		String discountType = "Discount";
		Price2 price2 =new Price2();
		price2.setPercentageDiscount(discount);
		price2.setPriceType(discountType);
		try {
			price2.setFromDate(dateSaving());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		price2Dao.savePrice(price2);
		
	}
	
	public void saveGenericThePrice(double discount, int id) {
		String discountType = "Discount";
		Price2 price2 =new Price2();
		price2.setId(id);
		price2.setPercentageDiscount(discount);
		price2.setPriceType(discountType);
		try {
			price2.setFromDate(dateSaving());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		price2Dao.savePrice(price2);
		
	}


	// save the discount when discounts are updated
	public void saveSpecilDiscount(List<Price2> price2, double discount,int i) 
	{
		String discountType = "Discount";
		//Price2 specialPrice  =new Price2();
		price2.get(i).setId(price2.get(i).getId());
		price2.get(i).setPercentageDiscount(discount);
		price2.get(i).setPriceType(discountType);
		price2.get(i).setProduct(price2.get(i).getProduct());
		price2.get(i).setToDate(null);

		
		try {
			price2.get(i).setFromDate(dateSaving());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		price2Dao.savePrice(price2.get(i));
		
		
	}
	
	// when new discount for a price for new products comming
	public void saveSpecilDiscount(Price2 price2, double discount) 
	{
		String discountType = "Discount";
		Price2 specialPrice  =new Price2();
		
		specialPrice.setPercentageDiscount(discount);
		specialPrice.setPriceType(discountType);
		specialPrice.setProduct(price2.getProduct());
		specialPrice.setToDate(null);
		try {
			specialPrice.setFromDate(dateSaving());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		price2Dao.savePrice(specialPrice);
		
		
	}

	
	
	//new discount used now
	
	public List<PricesWithDiscount> findDiscounts3(List<Price2> allDiscountPrice) 
	{
		List<Price2> discounts = allDiscountPrice;
		List<Price2> allbasePrice = findAllBasicPrice();
		
		List<PricesWithDiscount> discountPrices = new ArrayList<>();
		
		System.out.println("in findDiscount the allDiscount Price is " + discounts.size()); 
		System.out.println("in findDiscount the basePrice size is " +allbasePrice.size()); 
		
		
		for(int i = 0; i< discounts.size(); i++) {
			
			Price2 discountPrice = discounts.get(i);
			 System.out.println("discount started 0 the discount price pro id is " + discountPrice.getProduct().getProductId() );
			 
			for (int j = 0; j < allbasePrice.size(); j++) {
				 
				 
				Price2 basePrice = allbasePrice.get(j);
				System.out.println("discount started 1 base price id is" + basePrice.getProduct().getProductId());
				if(discountPrice.getProduct().getProductId().equals(basePrice.getProduct().getProductId())  ) {
					
					 System.out.println("discount started");
					 
					 double discountPercentage = discountPrice.getPercentageDiscount()/100;
					 System.out.println("discountpercentage started" + discountPercentage);
					 
					 double  getBasePrice = basePrice.getPrice();
					 System.out.println("getBasePrice " + getBasePrice);
					 
					 double setDiscountPrice = getBasePrice - (getBasePrice * discountPercentage);
					 System.out.println("discountPrice " + setDiscountPrice);
					 
					 PricesWithDiscount pricesWithDiscount = new PricesWithDiscount();
					 pricesWithDiscount.setBasicPrice(getBasePrice);
					 pricesWithDiscount.setDiscountPercentage(discountPercentage);
					 pricesWithDiscount.setDiscountPrice(setDiscountPrice);
					 pricesWithDiscount.setProId(discountPrice.getProduct());
					 discountPrices.add(pricesWithDiscount);

				}
				
			}
			
		}
		

		return discountPrices;
		
}
	public List<Price2> findProductPriceForUpdate(String productId){
		return price2Dao.findProductPriceForUpdate(productId);
	}
	
	
	public PricesWithDiscount findGenericDiscount(String productId) {
		Optional<Price2> price2 =price2Dao.genericDiscount();
		Optional<Price2> basePrices = price2Dao.findProductBasicPrice(productId);
		PricesWithDiscount pricesWithDiscount = new PricesWithDiscount();
		if(!price2.isEmpty()) {
			double discountPercentage = price2.get().getPercentageDiscount();
			double basePrice = basePrices.get().getPrice();
			double discountPrice = basePrice - ((basePrice * discountPercentage)/100);
			
			
			pricesWithDiscount.setBasicPrice(basePrice);
			pricesWithDiscount.setDiscountPercentage(discountPercentage);
			pricesWithDiscount.setDiscountPrice(discountPrice);
			
		}
			System.out.println("the Generic Discount is " + price2);		
		return pricesWithDiscount ;
	}

	public Optional<Price2> findGenericAllDiscount(){
		Optional<Price2> price2 =price2Dao.genericDiscount();
		return price2;
	}
}
