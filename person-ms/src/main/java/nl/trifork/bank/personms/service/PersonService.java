package nl.trifork.bank.personms.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.trifork.bank.personms.model.Transfer;
import nl.trifork.bank.personms.DAO.PersonDAO;
import nl.trifork.bank.personms.client.*;
import nl.trifork.bank.personms.model.*;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.slf4j.Logger; //logback
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.cloud.context.named.NamedContextFactory.Specification;
@Service
public class PersonService {

    private PersonDAO personDAO;
    private AccountClient accountClient;
    private TransferClient transferClient;
    @Autowired
    public PersonService(PersonDAO personDAO, AccountClient accountClient, TransferClient transferClient){
        this.personDAO = personDAO;
        this.accountClient = accountClient;
        this.transferClient = transferClient;
    }


    /*------------------------ CREATE PERSON METHODS ----------------------*/
    public Person createPerson (Person person, Role role) throws IOException {
        if(person.getPassword().equals(person.getConfirmPassword()))
        {
            //create person
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String securepassword = passwordEncoder.encode(person.getPassword());
            person.setPassword(securepassword);
            person.setCreated_at(new Date());
            person.setRole(role);
            personDAO.save(person);
            //create account
            String accountName = "AccountOf"+person.getFirstName()+"_"+person.getLastName();
            String accountDescription = "This is the automatic created account of "+person.getFirstName()+"_"+person.getLastName();
            String roleString = String.valueOf(person.getRole());
            Account account = new Account();
            account.setUserId(person.getId());
            account.setName(accountName);
            account.setDescription(accountDescription);
            accountClient.create(String.valueOf(person.getId()) , account).getBody();
            if(roleString.equals("USER"))
            {
                //create transfer of 100 euro from admin to user
                ResponseEntity<List<Account>> adminResponse = accountClient.findByUser(Long.valueOf(1));
                ResponseEntity<List<Account>> userResponse = accountClient.findByUser(person.getId());
                String fromKey = adminResponse.getBody().get(0).getKey();
                String toKey = userResponse.getBody().get(0).getKey();
                Transfer transfer = new Transfer(Long.valueOf("100"), fromKey, toKey, 1, "Welkoms cadeau", new Date());
                transferClient.createTransfer(transfer).getBody();
            }
        }
        return person;
    }



    /*------------------------ READ PERSON METHODS ----------------------*/

    public Person findPersonById(long id) {
        if (personDAO.exists(id)) {
            return personDAO.findPersonById(id);
        }
        return null;
    }

    public Iterable<Person> findAll(){
        return personDAO.findAll();
    }

    public Person findPersonByEmail(String email){
        return personDAO.findPersonByEmail(email);
    }

    public Person findPersonByFirstName(String firstName){
        return personDAO.findPersonByFirstName(firstName);
    }

    public Person findPersonByLastName(String lastName){
        return personDAO.findPersonByLastName(lastName);
    }

    public Person findPersonByRole(String role){
        return personDAO.findPersonByRole(role);
    }

    //    Functions
    protected static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte b : bytes) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}
