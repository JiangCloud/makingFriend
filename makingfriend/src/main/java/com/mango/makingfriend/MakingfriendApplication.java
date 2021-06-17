package com.mango.makingfriend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing // 使用jpa自动赋值
@EnableJpaRepositories(basePackages = "com.mango.makingfriend.dao")
@EntityScan(basePackages = "com.mango.makingfriend.model")
@EnableCaching
public class MakingfriendApplication extends SpringBootServletInitializer  {

    public static void main(String[] args) {
        SpringApplication.run(MakingfriendApplication.class, args);
    }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MakingfriendApplication.class);
    }
}
