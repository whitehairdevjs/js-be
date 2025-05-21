package com.example.js;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.js.mapper")
public class JsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsApplication.class, args);
	}

}
