package com.trhoanglee.expense.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

    @Bean
    public Docket swaggerSpringfoxDocket() {
        logger.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .select()
            .paths(regex("/api/.*"))
            .build();

        watch.stop();
        logger.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());

        return docket;
    }
}
