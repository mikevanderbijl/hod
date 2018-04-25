package nl.trifork.bank.personms;

import nl.trifork.bank.personms.model.Role;
import nl.trifork.bank.personms.service.PersonService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import nl.trifork.bank.personms.model.Person;
import org.springframework.cloud.client.circuitbreaker.*;
import org.springframework.cloud.client.discovery.*;
import org.springframework.cloud.netflix.feign.*;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.*;

import java.io.IOException;
@Configuration
@EnableFeignClients
@ComponentScan
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@EnableCircuitBreaker
public class PersonMsApplication {

    private static PersonService personService;


    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

	public static void main(String[] args) throws IOException {
		SpringApplication.run(PersonMsApplication.class, args);
        Person person = new Person();
        person.setPassword("theBestPasswordForAAdminMLTCD");
        person.setConfirmPassword("theBestPasswordForAAdminMLTCD");
        person.setFirstName("LarsThomChrisKonradDouweMike");
        person.setLastName("Trifork");
        person.setEmail("info@seapbank.nl");
        personService.createPerson(person, Role.ADMIN);
    }
}
