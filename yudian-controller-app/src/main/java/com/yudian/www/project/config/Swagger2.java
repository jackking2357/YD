package com.yudian.www.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2WebMvc
public class Swagger2 {

    @Bean
    public Docket businessApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("业务接口")
                .apiInfo(new ApiInfoBuilder().title("业务接口").description("业务接口接口").termsOfServiceUrl("").version("1.0").build()).select()
                .apis(RequestHandlerSelectors.basePackage("com.yudian.www.controller"))
                .paths(PathSelectors
                        .regex(".*/account-outin/.*")
                        .or(PathSelectors.regex(".*/account-robot/.*"))
                        .or(PathSelectors.regex(".*/account-robot-accelerator/.*"))
                        .or(PathSelectors.regex(".*/robot-accelerator/.*"))
                        .or(PathSelectors.regex(".*/robot/.*"))
                        .or(PathSelectors.regex(".*/robot-order/.*"))
                        .or(PathSelectors.regex(".*/work-task/.*"))
                        .or(PathSelectors.regex(".*/suggestion/.*"))
                        .or(PathSelectors.regex(".*/upload/.*"))
                )
                .build()
                .globalOperationParameters(getParameters());
    }

    @Bean
    public Docket loginApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("开放接口文档")
                .apiInfo(new ApiInfoBuilder().title("开放接口文档").description("开放接口文档").termsOfServiceUrl("").version("1.0").build()).select()
                .apis(RequestHandlerSelectors.basePackage("com.yudian.www.controller"))
                .paths(PathSelectors.regex(".*/public/.*")
                        .or(PathSelectors.regex(".*/login-next/.*")))
                .build()
                .globalOperationParameters(getParameters());
    }

    private List<Parameter> getParameters() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization").description("用户标识").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }
}
