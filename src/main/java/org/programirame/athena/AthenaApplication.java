package org.programirame.athena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@ServletComponentScan({"com.vaadin.wscdn"})
@PropertySources(value = {@PropertySource("classpath:url.properties"),
@PropertySource("classpath:application.properties")})
public class AthenaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AthenaApplication.class, args);
    }


}
