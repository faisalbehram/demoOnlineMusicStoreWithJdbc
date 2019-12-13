package com.onlinemusicstore.app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Role;
import com.onlinemusicstore.app.models.User;
import com.onlinemusicstore.app.repository.RoleRepoJdbc;
import com.onlinemusicstore.app.repository.UserJdbcRepo;

@Repository
public class UsersDao {
	
//	@Autowired
//	private UserRepositry userRepositry;
	
	@Autowired
	private UserJdbcRepo userJdbcsRepo;
	
	@Autowired
	private RoleRepoJdbc roleRepoJdbc;

	
	public void saveUser(User getuser) {
		Role role = roleRepoJdbc.findIdByRoleName("USER");
		
		
		System.out.println("the user  save and role id is" + role.getId());
		User user = userJdbcsRepo.save(getuser);
		roleRepoJdbc.saveRole ( user.getId(),role.getId());
	}
	
	public User getUserByEmail(String email) {
		User getUser = userJdbcsRepo.findByEmail(email);
		System.out.println("the customer iss " + getUser.getEmail());
		return getUser;
	}
	
}
