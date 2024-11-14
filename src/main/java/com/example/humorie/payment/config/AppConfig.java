package com.example.humorie.payment.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${portOne.api}")
    String apiKey;

    @Value("${portOne.secret}")
    String secretKey;

    @Bean
    public IamportClient iamportClient(){
        return new IamportClient(apiKey, secretKey);
    }
}
