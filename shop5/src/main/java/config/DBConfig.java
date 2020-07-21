package config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class DBConfig {

	@Bean(destroyMethod = "close") // destroyMethod:  소멸 시 실행될 메서드
	public DataSource dataSource() {
		ComboPooledDataSource ds = new ComboPooledDataSource(); // Connection Pool 객체
		try {
			ds.setDriverClass("org.mariadb.jdbc.Driver");
			ds.setJdbcUrl("jdbc:mariadb://localhost:3306/classdb");
			ds.setUser("scott");
			ds.setPassword("1234");
			ds.setMaxPoolSize(20); // 최대 연결 객체의 갯수
			ds.setMinPoolSize(3); // 최소 연결 객체의 갯수
			ds.setInitialPoolSize(5); // 초기 연결 객체의 갯수
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		// myBatis 설정
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource());
		bean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		return bean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception{
		// myBatis를 연결하기 위한 Session 객체 생성
		return new SqlSessionTemplate(sqlSessionFactory());
	}
}
