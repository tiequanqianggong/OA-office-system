package com.ruoyi.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Date: 2023.09.12.17:30
 * @Author: 子康
 * @Description：Swagger配置文件
 */
@Configuration
public class TestCaseSwaggerConfig {


    /**
     * 创建API
     */
    @Bean
    public Docket createTestCaseApi()
    {
        return new Docket(DocumentationType.OAS_30)
                // 是否启用Swagger
                .enable(true)

                // 配置扫描规则
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ruoyi.controller.testCase"))
                .paths(PathSelectors.any())
                .build()
                .groupName("测试用例模块")
                .apiInfo(apiInfo());
    }



    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo()
    {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("测试用例_接口文档")
                // 描述
                .description("描述：测试用例接口文档")
                // 作者信息

                // 版本
                .version("版本号:1.0.0")
                .build();
    }
}
