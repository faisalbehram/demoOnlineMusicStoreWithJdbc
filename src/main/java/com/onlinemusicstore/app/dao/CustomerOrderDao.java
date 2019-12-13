package com.onlinemusicstore.app.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.onlinemusicstore.app.models.CustomerOrder;
import com.onlinemusicstore.app.repository.CustomerOrderRepoJdbc;



@Repository
@Transactional
public class CustomerOrderDao{

       
//        @Autowired 
//        CustomerOrderRepository customerOrderRepo;
        
        @Autowired
        CustomerOrderRepoJdbc customerOrderRepoJdbc;
        
        public void addCustomerOrder(CustomerOrder customerOrder) {
        	System.out.println("customer Order are saved");
        	customerOrderRepoJdbc.save(customerOrder);
        }
        
		public List<CustomerOrder> getAllOrder() {
			
			return customerOrderRepoJdbc.customerOrderfindAll();
		}
}
