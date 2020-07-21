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

import dao.mapper.BoardMapper;
import logic.Board;


@Repository
public class BoardDao {
	
	@Autowired
	private SqlSessionTemplate template;
	private Map <String, Object> param = new HashMap <String, Object> ();

	public int maxnum() {
		return template.getMapper(BoardMapper.class).maxnum();
	}

	public void insert(Board board) {
		template.getMapper(BoardMapper.class).insert(board);
	}

	public List<Board> getBoardAll(int start, int limit, String searchtype, String searchcontent) {
		param.clear();
		param.put("start", start);
		param.put("limit", limit);
		param.put("searchtype", searchtype);
		param.put("searchcontent", searchcontent);
		return template.getMapper(BoardMapper.class).select(param);
	}

	public int countnum(String searchtype, String searchcontent) {
		param.clear();
		System.out.println(searchtype+" , "+searchcontent);
		param.put("searchtype", searchtype);
		param.put("searchcontent", searchcontent);
		return template.getMapper(BoardMapper.class).countnum(param);
	}

	public Board getBoard(Integer num) {
		param.clear();
		param.put("num", num);
		return template.getMapper(BoardMapper.class).select(param).get(0);
	}

	public void updateReadCnt(Integer num) {
		param.clear();
		param.put("num", num);
		template.getMapper(BoardMapper.class).updateReadCnt(param);
	}

	public void grpstepAdd(Board board) {
		template.getMapper(BoardMapper.class).grpstepAdd(board);
	}

	public void update(Board board) {
		template.getMapper(BoardMapper.class).update(board);
	}

	public void delete(int num) {
		param.clear();
		param.put("num", num);
		template.getMapper(BoardMapper.class).delete(param);
	}

	public List<Map<String, Object>> graph1() {
		return template.getMapper(BoardMapper.class).graph1();
	}

	public List<Map<String, Object>> graph2() {
		return template.getMapper(BoardMapper.class).graph2();
	}

	
	

}
