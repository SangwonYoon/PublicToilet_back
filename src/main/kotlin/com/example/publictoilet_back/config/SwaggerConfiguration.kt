package com.example.publictoilet_back.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    @Bean
    fun api() : Docket{
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.publictoilet_back.controller"))
                .paths(PathSelectors.any())
                .build()
    }

    private fun apiInfo() : ApiInfo{
        return ApiInfoBuilder()
                .title("공중 화장실 위치 정보 제공 서비스 API")
                .description("공중 화장실 위치 정보 제공 서비스 API 명세서")
                .version("1.0.0")
                .build()
    }
}