package com.dislinkt.postsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PostsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostsApiApplication.class, args);
	}

}
