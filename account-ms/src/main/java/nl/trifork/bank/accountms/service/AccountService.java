package nl.trifork.bank.accountms.service;

import nl.trifork.bank.accountms.model.Transfer;
import org.slf4j.Logger; //logback
import org.slf4j.LoggerFactory;
import nl.trifork.bank.accountms.DAO.AccountDAO;
import nl.trifork.bank.accountms.exception.AccountException;
import nl.trifork.bank.accountms.model.Account;
import nl.trifork.bank.accountms.validator.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;


import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import static org.aspectj.util.LangUtil.isEmpty;

@Service
public class AccountService extends InputValidator {

    @Autowired
    private final AccountDAO accountDAO;
    private final static Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /*------------------------ CREATE SERVICE METHODS ----------------------*/

    /**
    * @param request - Webrequest, used to extract metadata & headers from request. We can use this to get OAuth token from header.
    * @param account - Account, JSON Account, only name and description are used, so just a json containing those should suffice.
    *
    * @return ResponseEntity - HttpResponse, We return an OK status and put the new account in the body.
    * */
    public ResponseEntity<?> create(WebRequest request, Account account) throws AccountException {
        logger.info("Service - method create account with request, and account(name, description)");
        String key = generateKey();
        String name        = account.getName();
        String description = account.getDescription();

        long userId     = Long.parseLong(request.getHeader("userId"));
        long minbalance = 0;
        long balance    = 500;
        logger.info("Creating account with [key: {}, userId: {}, name: {}, desc: {}, minBalance: {}, balance: {}]",
                key, userId, name, description, minbalance, balance);
        Account acc = new Account(name, description);
        logger.info("Account created: {}.  ", acc);

        logger.info("Setting values: key , userId, minBalance & balance to set values.");
        acc.setKey(key);
        acc.setUserId(userId);
        acc.setMinBalance(minbalance);
        acc.setBalance(balance);
        logger.info("Service - Saving account : {}", acc);
        accountDAO.save(acc);

        logger.info("Returning ok responseEntity holding account.");
        return ResponseEntity.ok().body(acc);
    }


    /**
     * Generates a BigInteger (immutable) with 40 numbers, we turn this into a string and take 9 digits to use as a key.
     * If the key exists, we increment the begin and end of the substring to get another key value.
     *
     * @return UUIDKey - String
     * */
    private String generateKey() {
        int start  = 1;
        int end    = 10;

        String bigKey =  String.format("%040d",new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
        String UUIDKey = String.valueOf(bigKey.substring(1,10));
        logger.info("Created key to use for account: {}", UUIDKey);

        while (accountExists(UUIDKey)) {
            if (end >= 39) {
                UUIDKey = String.valueOf(Long.parseLong(UUIDKey) + 1);
            } else {
                UUIDKey = String.valueOf(bigKey.substring(++start, ++end));
            }
        }

        logger.info("Key is valid, returning to caller.");
        return UUIDKey;
    }
    /*------------------------- FIND SERVICE METHODS -----------------------*/
    public Account findAccountByUserId(long id) {
        return accountDAO.findOne(id);
    }


    /**
     * Called to find an account either internally or by external microservices. Set's an E-Tag header, which contains the version of the account at the time of response.
     * The value of the version in the E-Tag can be used by the transfer-ms to be set as If-Match header.
     *
     * @param key - String, key of the account being searched.
     * @return ResponseEntity, if account with key is found, return the account as body and version as E-Tag header
     *                         if the account is not found, we return a 404 not found.
     * */
    public ResponseEntity<?> findAccount(String key) throws AccountException  {

        if (!validateKey(key)) {
            throw new AccountException("Trying to find an account using an invalid key. ");
        }

        Account account = accountDAO.findAccountByKey(key);
        if (account == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity
                    .ok()
                    .eTag("\"" + account.getVersion() + "\"")
                    .body(account);
        }
    }

    public List<Account> findUser(long userId) throws AccountException {

        if (!validateUserId(userId)) {
            throw new AccountException("Trying to find accounts using an invalid userId");
        }

        return accountDAO.findAccountsByUserId(userId);
    }

    /*----------------------- UPDATE SERVICE METHODS -----------------------*/


    public Account updateBalance(String key, long balance) throws AccountException{

        if (!validateKey(key)) {
            throw new AccountException("Trying to update balance using invalid key");
        }

        if (!validateLong(balance)) {
            throw new AccountException("Trying to update balance using invalid balance");
        }

        Account account = accountDAO.findAccountByKey(key);
        account.setBalance(balance);
        return accountDAO.save(account);
    }

    public Account updateMinBalance(String key, long minBalance) throws AccountException {

        if (!validateKey(key)) {
            throw new AccountException("Trying to update minBalance using invalid key");
        }

        if (!validateLong(minBalance)) {
            throw new AccountException("Trying to update minBalance using invalid balance");
        }

        Account account = accountDAO.findAccountByKey(key);
        account.setMinBalance(minBalance);
        return accountDAO.save(account);
    }

    public Account updateDescription(String key, String description)  throws AccountException {

        if (!validateKey(key)) {
            throw new AccountException("Trying to update description using invalid key");
        }

        if (!validateString(description)) {
            throw new AccountException("Trying to update description using invalid balance");
        }

        Account account = accountDAO.findAccountByKey(key);
        account.setDescription(description);
        return accountDAO.save(account);
    }


    /**
     * Once transfer-ms receives the E-Tag version from & to accounts from findByKey(),
     * the If-Match header required for this function will be set to that value.
     * In this method we compare the version from the fromKey passed in transfer to the current version.
     *
     * If the versions don't match then the fromAccount has been updated in the period of the transfer, and we discontinue it.
     * If the versions match, we update and return the HttpStatus 418. TOBE CHANGED
     *
     * @param request - Webrequest, used to extract header "If-Match", which we require to compare the current Account version against.
     *                If this is missing, or not equal to the current version of account, we return a adequate response.
     * @param transfer - Transfer Object containing issuer, amount, fromKey & toKey, provided as JSON and used to match accounts
     *
     * @return I_AM_A_TEAPOT
     * */
    public ResponseEntity<?> transfer(WebRequest request, Transfer transfer) throws AccountException {

        long issuer  = transfer.getIssuer();
        long amount  = transfer.getAmount();
        String fromKey = transfer.getFrom_key();
        String toKey   = transfer.getTo_key();
        System.out.println(fromKey);
        System.out.println(toKey);
        Account fromAccount = accountDAO.findAccountByKey(fromKey);
        Account toAccount   = accountDAO.findAccountByKey(toKey);
        System.out.println(fromAccount);
        System.out.println(toAccount);
        if (fromAccount == null || toAccount == null) {
            return ResponseEntity.notFound().build();
        }

        String ifMatchValue = request.getHeader("If-Match");

        if (isEmpty(ifMatchValue)) {
            return ResponseEntity.badRequest().build();
        }

        if (!ifMatchValue.equals("\"" + fromAccount.getVersion() + "\"")) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }

        try {

            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);

            accountDAO.save(fromAccount);
            accountDAO.save(toAccount);

            return ResponseEntity
                    .ok()
                    .eTag("\"" + fromAccount.getVersion() + "\"")
                    .body(HttpStatus.I_AM_A_TEAPOT);

        } catch (OptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

}
