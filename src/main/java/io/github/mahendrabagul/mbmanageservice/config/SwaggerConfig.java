package io.github.mahendrabagul.mbmanageservice.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

//  @Bean
//  public Docket api() {
//    return new Docket(DocumentationType.SWAGGER_2)
//        .select()
//        .apis(RequestHandlerSelectors.any())
//        .paths(PathSelectors.any())
//        .build();
//  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(
            RequestHandlerSelectors.basePackage("io.github.mahendrabagul.mbmanageservice.resource"))
        .paths(PathSelectors.ant("/api/**"))
        .build().apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
        "MB Manage REST API",
        "MB Manage REST API for managing college admissions.",
        "API V1",
        "Terms of service",
        new Contact("Mahendra Bagul", "mahendrabagul.github.io", "bagulm123@gmail.com"),
        "License of API", "API license URL", Collections.emptyList());
  }
}