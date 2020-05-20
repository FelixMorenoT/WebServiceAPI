package com.web.service.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.web.service.api.dto.UsuariosDAO;
import com.web.service.api.services.usuario.UserAPI;

@SpringBootApplication
public class ApiWebServicesApplication {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserAPI.class);
	
	@Autowired
	private IApiWebServices userRepository;
	
	public static void main(String[] args) {
		LOG.debug("--- Start Web Services ---");
		SpringApplication.run(ApiWebServicesApplication.class, args);
	}
	
	@Bean
	InitializingBean initData() {
	       return () -> {
	           userRepository.save(new UsuariosDAO(1,"ADMIN","202cb962ac59075b964b07152d234b70","admin@hotmail.com","ADMIN",true));
	         };
	      }
}
