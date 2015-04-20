package com.eniro;

import com.eniro.api.BasicSearchClient;
import com.eniro.api.exception.AssertException;
import com.eniro.repository.impl.SearchHistoryRepoImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.eniro")
public class BasicSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicSearchApplication.class, args);
    }

    @Bean
    public BasicSearchClient basicSearchClient(@Value("${eniro.profile}") String profile, @Value("${eniro.key}") String key) throws AssertException {
        return new BasicSearchClient(profile, key);
    }
}
