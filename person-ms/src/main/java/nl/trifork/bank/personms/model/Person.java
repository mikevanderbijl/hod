package nl.trifork.bank.personms.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Person")
public class Person {

    public Person(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Please provide a valid e-mail")
    @NotEmpty(message = "Please provide an e-mail")
    private String email;

    @Column(name = "password")
    private String password;

    @Transient
    private String confirmpassword;

    @Column(name = "first_name")
    @NotEmpty(message = "Please provide your first name")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Please provide your last name")
    private String lastName;

    @Column(name = "role")
    private Role role;

    @Column(name = "created_at", updatable = false)
    private Date date;

    public String getAll() { return "naam: "+firstName+lastName+" email: "+email+" wachtwoord: "+password+" confirm: "+confirmpassword;}

    public Date getCreated_at() {return date;}

    public void setCreated_at(Date date){this.date = date;}

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getConfirmPassword() {
        return confirmpassword;
    }
    public void setConfirmPassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
