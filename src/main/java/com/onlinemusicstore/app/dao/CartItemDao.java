package com.onlinemusicstore.app.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Cart;
import com.onlinemusicstore.app.models.CartItem;
import com.onlinemusicstore.app.repository.CartItemRepositoryJdbc;

@Repository
public class CartItemDao {

	
	
	@Autowired
	private CartItemRepositoryJdbc cartItemRepositoryJdbc;
	
	public void addCartItem(CartItem cartItem) 
	{
		cartItemRepositoryJdbc.save(cartItem);	
	}


	public void removeCartItembyId(int cartItemId) 
	{
		System.out.println("the deleted is "  );

		cartItemRepositoryJdbc.deleteCartItem(cartItemId);
		System.out.println("deleted ");	
	}
	
	public void removeByCartId(int cartId) 
	{
		System.out.println("in the cartItemDao is removie by cartId ");
		cartItemRepositoryJdbc.deleteCartItemByCartId(cartId);	
	}
	
	public List<CartItem> showAll()
	{
		List<CartItem> cartItems = cartItemRepositoryJdbc.findAll();
		return cartItems;
	}

	public List<CartItem> showMyCart(int cartId, Cart cart) 
	{
		System.out.println("in show my cart where card id is " + cartId);
		
		String cartIdd = new StringBuilder().append(cartId).toString();
		System.out.println("in show my cart where card id iss in string " + cartIdd);
		
		List<CartItem> cartItems = cartItemRepositoryJdbc.findByCart(cartId, cart);
		System.out.println("the cart item is " + cartItems);
		return cartItems;
	}


	public CartItem getItemById(int cartItemId) 
	{
		CartItem cartItem = cartItemRepositoryJdbc.findByCartItemId(cartItemId);
		System.out.println("the cartitem id is in DAO " + cartItem.getCartItemId());
		return cartItem;
	}


	
	

}
