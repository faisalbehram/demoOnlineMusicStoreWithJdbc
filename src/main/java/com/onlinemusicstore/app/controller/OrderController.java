package com.onlinemusicstore.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onlinemusicstore.app.models.Cart;
import com.onlinemusicstore.app.models.CartItem;
import com.onlinemusicstore.app.models.Customer;
import com.onlinemusicstore.app.models.CustomerOrder;
import com.onlinemusicstore.app.service.CartItemService;
import com.onlinemusicstore.app.service.CartService;
import com.onlinemusicstore.app.service.CustomerOrderService;

/**
 * Created by Le on 1/25/2016.
 */

@Controller
public class OrderController {

	//  to check out the cart this controller is used which update the order table
	// order are saved relative to there Id
	
    @Autowired
    private CartService cartService;
    
    @Autowired
    private CustomerOrderService customerOrderService;
    
    @Autowired
    private CartItemService cartItemService;
    	
    // check out for the orders 
    
    @RequestMapping("/checkout/{cartId}")
    public String showCustomerInfo(@PathVariable("cartId") int cartId,Model model) {
    	
    	System.out.println("in order controler");
        Cart cart=cartService.getCartbyIdForCheckOut(cartId);
    	System.out.println("in order controler the carrt is " + cart.getCartId());
    	System.out.println("in order controler the carrt is " + cart.getCustomer().getShippingAddress().getCountry());
        model.addAttribute("cart", cart);
        
        return "collectCustomerInfo.jsp";
    }
    
    // now when click on next it will show the user shipping address
    @RequestMapping("/checkout/shippingAddress/{cartId}")
    public String showShipping(@PathVariable("cartId") int cartId,Model model) 
    {
    	 Cart cart=cartService.getCartbyIdForCheckOutForBilling(cartId);
    	 model.addAttribute("cart", cart);
    	 return "../collectShippingDetail.jsp";
    }
    
    // this will show a recipe of the order where all grand total user info shiping info and product info are there
    @RequestMapping("/checkout/savingOrder/{cartId}")
    public String savingOrder(@PathVariable("cartId" )int cartId , Model model) {
    	System.out.println("in recipe");
    	//get the cart info
    	Cart cart=cartService.getCartbyIdForCheckOut(cartId);
    	Cart cart2Billing=cartService.getCartbyIdForCheckOutForBilling(cartId);
    	System.out.println("in GETCART BY ID IS " + cart);
 
    	// get all items on cart
    	
	   	 List<CartItem> cartItems = cartItemService.showCart(cartId);
	 	System.out.println("in all carts items " + cartItems);
	 	
	   	 model.addAttribute("cart", cart);
	   	 model.addAttribute("billing", cart2Billing);
	   	 model.addAttribute("cartItems", cartItems);
    	return "../orderConfirmation.jsp";
    }
    
    @RequestMapping("/checkout/savingConfirmed/{cartId}")
    public String savingConfirmed(@PathVariable("cartId") int cartId,Model model) {
    	System.out.println("in order controler");
        CustomerOrder customerOrder = new CustomerOrder();
        Cart cart=cartService.getCartbyIdForCheckOut(cartId);
        customerOrder.setCart(cart);
        Customer customer = cart.getCustomer();
        customerOrder.setCustomer(customer);
    
        customerOrder.setShippingAddress(customer.getShippingAddress());

        customerOrderService.addCustomerOrder(customerOrder);

    	return "../thankCustomer.jsp";
    }
    
    @RequestMapping("/checkout/checkOutCancelled")
    public String cancelled() {
    	return "checkOutCancelled.jsp";
    }
    
}
