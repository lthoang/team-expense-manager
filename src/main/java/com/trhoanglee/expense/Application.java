package com.trhoanglee.expense;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hoangtle
 */
@ComponentScan
@EnableAutoConfiguration
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class.getCanonicalName());
    public static void main(String... args) {
        ApplicationContext appContext = SpringApplication.run(Application.class, args);
        
        MemberService memberService = appContext.getBean(MemberService.class);
        LOG.info(memberService.ping());
        SpringApplication.exit(appContext);
    }
}
