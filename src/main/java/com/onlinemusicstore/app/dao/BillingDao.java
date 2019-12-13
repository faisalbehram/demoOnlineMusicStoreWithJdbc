//package com.onlinemusicstore.app.dao;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import com.onlinemusicstore.app.models.BillingAddress;
//import com.onlinemusicstore.app.repository.BillingJdbcRepo;
//
//@Repository
//public class BillingDao {
//	
////	@Autowired
////	private BillingRepository billingRepository;
//	
//	@Autowired
//	private BillingJdbcRepo billingJdbcRepo;
//	
//	public BillingAddress saveBilling(BillingAddress address,BillingAddress billingAddress) {
//		
//		billingAddress.setCustomer(address.getCustomer());
//		BillingAddress Id = billingJdbcRepo.savingBillingAddress(billingAddress);
//		
//		
//		
//		return Id;
//	}
//
//}
