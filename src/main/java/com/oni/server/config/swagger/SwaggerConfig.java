package com.oni.server.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)

public class SwaggerConfig {
	
	@Bean
	public Docket getDocket() {

		
		return new Docket(DocumentationType.SWAGGER_2)
		          .select()
		          .apis(RequestHandlerSelectors.basePackage("com.oni.server.endpoint"))
		          .paths(PathSelectors.any())
		          .build()
		          .apiInfo(apiInfo());
				
	}

	public ApiInfo apiInfo() {
		Contact contact= new Contact("Sandeep","sandeepURL","sandeepmalviya@gmail.com");
	
		String licenseUrl = ServletUriComponentsBuilder.fromPath("/eula.html")
		.toUriString();
		 return new ApiInfoBuilder().title("Invest19 API Documentation")
	                .description("Invest19 API").termsOfServiceUrl("")
	                .contact(contact)
	                .license("Invest19 EULA")
	                .licenseUrl(licenseUrl)
	                .version("1.0.0")
	                .build();
	}
}
