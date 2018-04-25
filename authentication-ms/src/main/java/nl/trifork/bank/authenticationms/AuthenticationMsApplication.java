package nl.trifork.bank.authenticationms;

import nl.trifork.bank.authenticationms.client.PersonClient;
import nl.trifork.bank.authenticationms.model.Person;
import nl.trifork.bank.authenticationms.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class AuthenticationMsApplication {
    public static void main(String[] args) {

        SpringApplication.run(AuthenticationMsApplication.class, args);

    }
}
