package com.onlinemusicstore.app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Cart;
import com.onlinemusicstore.app.repository.CardJdbcRepo;

@Repository
public class CartDao {
	
	@Autowired
	private CardJdbcRepo cartJdbcRepo;
	
	// saving the cart
	
	public Cart saveCart(Cart cart) {
	
		Cart id = cartJdbcRepo.savingCart(cart);
		System.out.println("the cart are saved");	
		return id;
	}
	
	
	public Cart getCartById(int cartId) {
		return cartJdbcRepo.findById(cartId);
	}

	public Cart getCartByIdForCHeckout(int cartId) {
		return cartJdbcRepo.findByCartIdForCheckOut(cartId);
	}
	
	public Cart getCartByIdForBilling(int cartId) {
		return cartJdbcRepo.findByCartIdForCheckoutBillingAddress(cartId);
	}

	
	
	public void UpdateCart(Cart cart,Double totalPrice) {
		if(totalPrice == 0.0) {
			Cart cart2 = cartJdbcRepo.findById(cart.getCartId());
			System.out.println(" in IF the price is  " + totalPrice + " // and the id is " + cart2 );
			cart2.setGrandTotal(totalPrice);
			cartJdbcRepo.updateingCart(cart2);	
			
		}
		else
		{
			Cart cart2 = cartJdbcRepo.findById(cart.getCartId());
			System.out.println("the price is in cartDao " + totalPrice + " // and the id is " + cart2.getCartId() );
			double newprice  = totalPrice +  cart2.getGrandTotal();
			System.out.println("the new price is " + newprice + "and the cart2 privce price is  " + cart2.getGrandTotal());
			cart2.setGrandTotal(newprice);
			cartJdbcRepo.updateingCart(cart2);	
		}
	}
	
//	public Cart Update(Cart cart)
//	{
//		int cartId = cart.getCartId();
//		double grandTotal = customerOrderService.getCustomerOrderGrandTotal(cartId);
//		cart.setGrandTotal(grandTotal);
//		return cartRepository.save(cart);
//	}
	

}
