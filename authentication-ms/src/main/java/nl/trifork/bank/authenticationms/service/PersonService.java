package nl.trifork.bank.authenticationms.service;

import nl.trifork.bank.authenticationms.client.PersonClient;
import nl.trifork.bank.authenticationms.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonClient personClient;

    public Person getByEmail(String s){
        return personClient.getByEmail(s).getBody();
    }
}
