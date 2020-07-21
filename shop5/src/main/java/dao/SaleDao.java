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

import dao.mapper.SaleMapper;
import logic.Item;
import logic.Sale;
import logic.SaleItem;


@Repository
public class SaleDao {
	
	@Autowired
	private SqlSessionTemplate template;
	private Map <String, Object> param = new HashMap <String, Object> ();
	
	
	public List<Sale> getSaleid() {
		return template.getMapper(SaleMapper.class).getSaleid();
	} 

	public void insert(Sale sale) {
		template.getMapper(SaleMapper.class).insert(sale);
	}

	public void insertSaleItem(SaleItem saleItem) {
		template.getMapper(SaleMapper.class).insertItem(saleItem);
	}

	public List<Sale> getlist(String id) {
		param.clear();
		param.put("userid", id);
		return template.getMapper(SaleMapper.class).select(param);
	}

	public List<SaleItem> getsaleitemlist(int saleid) {
		param.clear();
		param.put("saleid", saleid);
		return template.getMapper(SaleMapper.class).selectItem(param);
	}
	
	
}
