package com.likelion.bizup.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

/**
 * Swagger 설정
 */
@Configuration
public class SwaggerConfig {
	private static final String BEARER_TOKEN_PREFIX = "bearer";
	private static String securityJwtName = "JWT";

	@Bean
	public OpenAPI openAPI() {
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
		return new OpenAPI()
			.addServersItem(new Server().url("/").description("현재 서버"))
			.components(apiComponents())
			.addSecurityItem(securityRequirement)
			.info(apiInfo());
	}

	private Info apiInfo() {
		return new Info()
			.title("BIZUP backend API Docs")
			.description("소상공인 정책 소개 관련 웹 서비스")
			.version("1.0.0");
	}

	private Components apiComponents() {
		return new Components()
			.addSecuritySchemes(securityJwtName, new SecurityScheme()
				.name(securityJwtName)
				.type(SecurityScheme.Type.HTTP)
				.scheme(BEARER_TOKEN_PREFIX)
				.bearerFormat(securityJwtName));
	}
}