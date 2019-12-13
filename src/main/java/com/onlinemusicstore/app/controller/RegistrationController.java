package com.onlinemusicstore.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.onlinemusicstore.app.models.BillingAddress;
import com.onlinemusicstore.app.models.Customer;
import com.onlinemusicstore.app.models.ShippingAddress;
import com.onlinemusicstore.app.service.CustomerService;
import com.onlinemusicstore.app.util.SendTheMail;

@Controller
public class RegistrationController {
	//this controller is used to register the customer with full information
	//
	
	@Autowired
    private CustomerService customerService;

	@Autowired
	private SendTheMail sendTheMail;
	
	 @RequestMapping("/register")
	    public String registerCustomer(Model model) {

		 System.out.println("show registeration page");
	        Customer customer = new Customer();

	        ShippingAddress shippingAddress = new ShippingAddress();
	        BillingAddress billingAddress = new BillingAddress();
	        
	        billingAddress.setCustomer(customer);
	        billingAddress.setShippingAddress(shippingAddress);
	        
	      
	        
	        model.addAttribute("billingAddress", billingAddress);
	      

	        return "registerCustomer.jsp";

	    }
	 
	 @RequestMapping(value = "/registercustomer", method = RequestMethod.POST)
	    public String registerCustomerPost(@Valid @ModelAttribute("billingAddress") BillingAddress billingAddress, BindingResult result,
	                                       Model model) {
		
		
		 System.out.println("posting registration");
	        if (result.hasErrors()) {
	            return "registerCustomer.jsp";
	        }
	        Customer customer  = billingAddress.getCustomer();
	        ShippingAddress shippingAddress = billingAddress.getShippingAddress();
	        
	        customer.setEnabled(true);
	        customerService.addCustomer(customer,shippingAddress,billingAddress);
	        
	        
	      //  sendTheMail.mailSending(customer.getCustomerEmail());
		   	 System.out.println("the customer is" + customer );
		   	 System.out.println("the shiping is" + shippingAddress );
	      //  customerService.addCustomer(customer);
	   	 System.out.println("posting4 registration");
	   	 
	   //	 sendTheMail.mailSending(customer.getCustomerEmail());
	   	 

	        return "registerCustomerSuccess.jsp";
		 
		
	    }


}
