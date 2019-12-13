package com.onlinemusicstore.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Role;
import com.onlinemusicstore.app.models.User;

@Repository
public class UserJdbcRepo {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public User findByEmail(String email) {
		

		User user = new User();
		user= jdbcTemplate.queryForObject(
                "select * from user where email = ?",
                new Object[]{email},new RowMapper<User>() {

        			@Override
        			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        				User user = new User();
        				
        				user.setId(rs.getLong("id"));
        				user.setEmail(rs.getString("email"));
        				user.setUsername(rs.getString("username"));
        				user.setPassword(rs.getString("password"));
        				user.setVerified(rs.getBoolean("verified"));
        				Set<Role> role = new HashSet<Role>() ;
        				Role newRole = new Role();
        				newRole.setName(rs.getString("name"));
        				role.add(newRole);
        				user.setRoles(role);
//        				user.setRoles((Set<Role>) rs.getObject("roles"));
        				System.out.println("the roles is " + newRole.getName());
        				return user;
        			}
        		});
		
		return user;
		
	}

	public User save(User getuser) {
		KeyHolder holder = new GeneratedKeyHolder();
		String insertSql = "INSERT  INTO user (email, password, username, verified ) VALUES (:email, :password, :username, :verified)";
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("email", getuser.getEmail());
		parameters.addValue("password", getuser.getPassword());
		parameters.addValue("username", getuser.getUsername());
		parameters.addValue("verified", getuser.isVerified());
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		namedParameterJdbcTemplate.update(insertSql, parameters,holder);
		getuser.setId(holder.getKey().longValue());
		
		return getuser;
	}
	
	
// findbyusername will get user info with there role also
	public User findbyUserName(String username) {
		String searchSql ="select * from ((user inner join user_role on user.id = user_role.user_id AND username = :username )inner join role on role.id = user_role.role_id)";
		System.out.println("in findbyusername ");
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("username",username);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		User user = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(searchSql, parameters,new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				
				user.setId(rs.getLong("id"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setVerified(rs.getBoolean("verified"));
				Set<Role> role = new HashSet<Role>() ;
				Role newRole = new Role();
				newRole.setName(rs.getString("name"));
				role.add(newRole);
				user.setRoles(role);
//				user.setRoles((Set<Role>) rs.getObject("roles"));
				System.out.println("the roles is " + newRole.getName());
				return user;
			}
		}));
		System.out.println("in findbyusername  the user is " + user);
		
		return user;
		
	}
	
	
}
