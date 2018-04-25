package nl.trifork.bank.authenticationms.service;

import nl.trifork.bank.authenticationms.model.Person;
import nl.trifork.bank.authenticationms.model.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    public UserDetailsServiceImpl() {
    }

    @Autowired
    PersonService personService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Person person = personService.getByEmail(s);
        System.out.println(person.toString());
        return new UserDetailsImpl(person.getEmail(), person.getPassword());
    }
}
