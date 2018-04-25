package nl.trifork.bank.authenticationms.client;

import nl.trifork.bank.authenticationms.model.Person;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("person-ms")
public interface PersonClient {

    @RequestMapping(method = RequestMethod.GET, value = "/email/{email}")
    ResponseEntity<Person> getByEmail(@PathVariable(value = "email") String email);



}
