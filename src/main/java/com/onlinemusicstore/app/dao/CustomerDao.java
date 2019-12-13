package com.onlinemusicstore.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Customer;
import com.onlinemusicstore.app.repository.CustomerJdbcRepo;

@Repository
public class CustomerDao {
	
//	@Autowired
//	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerJdbcRepo customerJdbcRepo;
	
	
	public Customer saveCustomer(Customer customer) {
		System.out.println("the CUstomer are saved");
		return customerJdbcRepo.saveCustomer(customer);
		
	}
	
	public List<Customer> getAllCustomer(){
		return customerJdbcRepo.findAll();
	}
	 public Optional<Customer> getCustomerById(int customerId) {
		Customer checking =  customerJdbcRepo.findById(customerId);
		Optional<Customer> optionalCustomer = Optional.of(checking);
		 return optionalCustomer;
	 }

	public Customer getByUserName(String username) {
		System.out.println("in findbyUsername in customer Dao " );
		return customerJdbcRepo.findByCustomerUserName(username);
	}

	public Customer getByEmail(String email) {
		System.out.println("in findByEmail in customer " );
		//Customer  customer = customerRepository.findByCustomerEmail(email);
		
		Customer customer = customerJdbcRepo.findByCustomerEmail(email);
		System.out.println("in findByEmail in customer " + customer.getCustomerEmail() );
		return customer;
	}

	public void UpdateCustomer(int id, Customer customer) {
		 customerJdbcRepo.updateCustomer(id,customer);
		
	}

	
	

}
