package dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import logic.Sale;
import logic.SaleItem;

public interface SaleMapper {

	@Select("select ifnull(max(saleid), 0)+1 saleid from sale")
	List<Sale> getSaleid();

	@Insert("insert into sale (saleid, userid, saledate) values (#{saleid}, #{userid}, now())")
	void insert(Sale sale);

	@Insert("insert into saleitem (saleid, seq, itemid, quantity) values (#{saleid}, #{seq}, #{itemid}, #{quantity})")
	void insertItem(SaleItem saleItem);

	@Select("select * from sale where userid= #{userid}")
	List<Sale> select(Map<String, Object> param);

	@Select("select * from saleitem where saleid= #{saleid}")
	List<SaleItem> selectItem(Map<String, Object> param);

}
