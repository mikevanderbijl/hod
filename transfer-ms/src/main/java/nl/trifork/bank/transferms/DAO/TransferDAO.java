package nl.trifork.bank.transferms.DAO;

import nl.trifork.bank.transferms.model.Transfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferDAO extends CrudRepository<Transfer, Long> {

    List<Transfer> findAllByUserId(long id);

    Transfer findFirstByOrderByIdDesc();

}
