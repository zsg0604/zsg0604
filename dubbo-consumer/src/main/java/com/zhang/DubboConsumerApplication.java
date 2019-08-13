package com.zhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication  //注解标明该类是本程序的入口
@ImportResource("spring-dubbo-consumer.xml")
public class DubboConsumerApplication extends SpringBootServletInitializer {

    @Override

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        return application.sources(DubboConsumerApplication.class);

    }

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerApplication.class, args);
    }

}
