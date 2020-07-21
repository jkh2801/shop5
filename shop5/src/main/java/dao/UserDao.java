package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import dao.mapper.UserMapper;
import logic.User;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSessionTemplate template;
	private Map <String, Object> param = new HashMap <String, Object> ();
	
	public void insert(User user) {
		template.getMapper(UserMapper.class).insert(user);
	}
	
	public User selectOne(String userid) {
		param.clear();
		param.put("userid", userid);
		List <User> list = template.getMapper(UserMapper.class).select(param);
		if(list.size() == 0) return null;
		return list.get(0);
	}
	public void updateUserinfo(User user) {
		template.getMapper(UserMapper.class).update(user);
	}
	public void deleteUserinfo(String userid) {
		param.clear();
		param.put("userid", userid);
		template.getMapper(UserMapper.class).delete(param);
	}
	public List<User> getlistAll() {
		return template.getMapper(UserMapper.class).select(null);
	}
	public List<User> userlist(String[] idchks) {
		param.clear();
		param.put("userids", idchks);
		return template.getMapper(UserMapper.class).select(param);
	}
}
