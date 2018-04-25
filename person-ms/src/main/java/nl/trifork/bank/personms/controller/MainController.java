package nl.trifork.bank.personms.controller;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;
import nl.trifork.bank.personms.model.Person;
import nl.trifork.bank.personms.model.Role;
import nl.trifork.bank.personms.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class MainController {

    private PersonService personService;
    protected Logger logger = Logger.getLogger(MainController.class.getName());


    @Autowired
    public void setPersonService(PersonService personService) {

        this.personService = personService;
    }

    /*------------------------ MAIN CONTROLLER METHODS ----------------------*/


    /*------------------------ CREATE CONTROLLER METHODS ----------------------*/
    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public void createUser(@RequestBody Person person) {
        System.out.println(person.getAll());
        try{
            personService.createPerson(person, Role.USER);
        } catch (Exception ex){
            System.out.println("Accounts bestaat al in de database");
        }
    }

    @RequestMapping(value = "/registerUser2", method = RequestMethod.POST)
    public void createUser2(@RequestBody String person) {
        System.out.println(person);
    }

    /*------------------------ FIND CONTROLLER METHODS ----------------------*/

    @RequestMapping("/findByKey/{id}")
        public @ResponseBody
        Person getAttr(@PathVariable(value="id") int id){
            return personService.findPersonById(id);
    }

    @RequestMapping("/findByFirstName/{firstName}")
        public @ResponseBody
        Person firstName(@PathVariable(value="firstName") String firstName){
            return personService.findPersonByFirstName(firstName);
    }

    @RequestMapping("/findByLastName/{LastName}")
        public @ResponseBody
        Person LastName(@PathVariable(value="LastName") String LastName){
            return personService.findPersonByLastName(LastName);
    }

    @RequestMapping("/findByEmail/{email}")
        public @ResponseBody
        Person email(@PathVariable(value="email") String email){
            return personService.findPersonByEmail(email);
    }

    @RequestMapping("/findByRole/{role}")
        public @ResponseBody
        Person Role(@PathVariable(value="role") String role){
            return personService.findPersonByRole(role);
    }

    @RequestMapping("/findAll")
        public Iterable<Person> findAll(){
            return personService.findAll();
    }
}
