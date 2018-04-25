package nl.trifork.bank.accountms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@ComponentScan
//@EnableTransactionManagement
@EnableDiscoveryClient
//@EnableCircuitBreaker
//@EnableFeignClients
@SpringBootApplication
public class AccountMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountMsApplication.class, args);
	}
}
