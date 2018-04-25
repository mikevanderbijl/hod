package nl.trifork.bank.personms.DAO;

        import nl.trifork.bank.personms.model.Person;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.stereotype.Repository;

@Repository
public interface PersonDAO extends CrudRepository<Person, Long> {

    Person findPersonById(long id);

    Person findPersonByEmail(String email);

    Person findPersonByFirstName(String firstName);

    Person findPersonByLastName(String lastName);

    Person findPersonByRole(String role);

    Person findPersonByIdIsNotNull();

}



