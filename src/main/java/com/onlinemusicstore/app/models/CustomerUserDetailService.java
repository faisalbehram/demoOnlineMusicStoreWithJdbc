package com.onlinemusicstore.app.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.onlinemusicstore.app.repository.UserJdbcRepo;

@Service
public class CustomerUserDetailService implements UserDetailsService{
	
//	@Autowired
//	private UserRepositry userRepositry;
	
	@Autowired
	private UserJdbcRepo userJdbcRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("the user is searching " +username);
		User user = userJdbcRepo.findbyUserName(username);
		System.out.println("the user is found " +user);
		
		if(user==null) {
			throw new UsernameNotFoundException("No user present with username: "+username);
		}
		else
			return new CustomerUserDetails(user);
	}
	 


}
