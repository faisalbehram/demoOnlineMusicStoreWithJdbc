package com.onlinemusicstore.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.onlinemusicstore.app.models.Cart;
import com.onlinemusicstore.app.models.Role;

@Repository
public class RoleRepoJdbc {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	

	
	public Role findIdByRoleName(String name) {
		KeyHolder holder = new GeneratedKeyHolder();
		
		String sql  = "select * from role where name = :roleName";
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("roleName", name);
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		Role role = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(sql, parameters,new RowMapper<Role>() 
		{

			@Override
			public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
				Role role = new Role();
				role.setId(rs.getLong("id"));
				role.setName(rs.getString("name"));
				return role;
			}
			
		}));
		
		
		return role;
		
	}



	public void saveRole(Long userId, Long roleId) {
		String sql = "insert into user_role (user_id, role_id) values (:userId, :roleId)";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("userId", userId);
		parameters.addValue("roleId", roleId);
		System.out.println("the user id is " + userId + "the role id is" + userId);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		namedParameterJdbcTemplate.update(sql, parameters);
		
		
		
	}

}
