package com.mango.makingfriend.util;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class Swagger2Config {

    @Bean
    public Docket createRestApi(){


        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("com.mango.makingfriend.controller"))
                .paths(PathSelectors.any()).build();


    }


    private ApiInfo apiInfo(){

        return  new ApiInfoBuilder()
                .title("社交系统。")
                .description("微服务swagger")
                .licenseUrl("www.hao123.com")
                .contact(new Contact("一团云", "", ""))
                .version("1.0").build();

    }
}
