package config;

import java.util.Properties;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration // 자바 설정 파일로 지정
@ComponentScan(basePackages = {"controller", "logic", "aop", "websocket", "dao"}) // 해당 패키지를 스캔하여 객체화
@EnableAspectJAutoProxy // aop 설정
@EnableWebMvc // 유효성 검증을 위한 설정
public class MVCConfig implements WebMvcConfigurer{
	
	@Bean // 객체화시켜 Container에 저장
	public HandlerMapping handlerMapping() { // url과 Controller을 매핑
		RequestMappingHandlerMapping hm = new RequestMappingHandlerMapping(); 
		hm.setOrder(0);
		return hm;
	}
	
	@Bean
	public ViewResolver viewResolver() { // view 결정자
		InternalResourceViewResolver vr = new InternalResourceViewResolver();
		vr.setPrefix("/WEB-INF/view/");
		vr.setSuffix(".jsp");
		return vr;
	}
	
	@Bean
	public MessageSource messageSource() { // properties 이름 설정
		ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
		ms.setBasename("messages"); // 유효성 메시지 설정
		return ms;
	}
	
	@Bean
	public MultipartResolver multipartResolver() { // 파일 업로드 설정
		CommonsMultipartResolver mr = new CommonsMultipartResolver();
		mr.setMaxInMemorySize(10485760); // 10MB까지는 메모리에 저장 (그 이상은 내부적으로 임시 파일로 저장)
		mr.setMaxUploadSize(104857600); // 100MB까지 업로드 가능
		return mr;
	}
	
	@Bean
	public SimpleMappingExceptionResolver exceptionHandler() { // 예외처리 
		SimpleMappingExceptionResolver ser = new SimpleMappingExceptionResolver();
		Properties pr = new Properties();
		pr.put("exception.CartEmptyException", "exception"); // exception.jsp로 넘어간다.
		pr.put("exception.LoginException", "exception");
		pr.put("exception.BoardException", "exception");
		ser.setExceptionMappings(pr);
		return ser;
		
	}
}
