package org.web3j.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.web3j.protocol.Web3j;
import org.web3j.spring.actuate.Web3jHealthIndicator;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


//    @Bean
//    public Web3j getWeb3j() {
//        return Web3j.build(new HttpService("https://mainnet.infura.io/A6YAM6J99HuuW8LafBEv"));
//    }

//   @Bean
//   public Web3j getWeb3j() {
//        return Web3j.build(new HttpService("https://ropsten.infura.io/A6YAM6J99HuuW8LafBEv"));
//    }

    @Bean
    public  RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Web3jHealthIndicator Web3jHealthIndicator(Web3j web3j) {
        return new Web3jHealthIndicator(web3j);
    }



}
