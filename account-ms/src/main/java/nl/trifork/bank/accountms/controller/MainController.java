package nl.trifork.bank.accountms.controller;

import nl.trifork.bank.accountms.exception.AccountException;
import nl.trifork.bank.accountms.model.Account;
import nl.trifork.bank.accountms.model.Transfer;
import nl.trifork.bank.accountms.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RequestMapping("/accounts")
@RestController
public class MainController {

    private final AccountService accountService;
    private final static Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    public MainController(AccountService accountService) {
        this.accountService = accountService;
    }


    /* ------------------------ MAIN CONTROLLER METHODS ------------------------*/
    @RequestMapping("/index")
    public String account() {
        return "Hello world!";
    }

    /*------------------------ CREATE CONTROLLER METHODS ----------------------*/

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(WebRequest request, @RequestBody Account account)  {
        try {
            return accountService.create(request, account);
        } catch (AccountException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /*-------------------------- FIND CONTROL METHODS ------------------------*/

    @RequestMapping(value="/account/{key}", method = RequestMethod.GET)
    public ResponseEntity<?> findAccount(@PathVariable("key") String key) {

        try {
            return accountService.findAccount(key);
        } catch (AccountException e) {
            logger.warn(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value="/user/{userId}", method=RequestMethod.GET)
    public List<Account> findUser(@PathVariable("userId") Long userId) {

        try {
            return accountService.findUser(userId);
        } catch (AccountException e) {
            logger.warn(e.getMessage());
        }
        return null;
    }

    /*------------------------ UPDATE CONTROL METHODS ------------------------*/

    /* TODO Change to id*/
    @RequestMapping(value="/account/{key}/balance", method=RequestMethod.PUT)
    public Account updateBalance(@PathVariable("key") String key,
                                        @RequestParam("balance") Long balance) {
        System.out.println(String.format("Balance updated for Account: %d, new balance: %d", key, balance));
        try {
            return accountService.updateBalance(key, balance);
        } catch (AccountException e) {
            logger.warn(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value="/account/{key}/min-balance", method=RequestMethod.PUT)
    public Account updateMinBalance(@PathVariable("key") String key,
                                           @RequestParam("minBalance") Long minBalance) {
        System.out.println(String.format("Minimum balance updated for Account: %d, new minimum balance: %d", key, minBalance));

        try {
            return accountService.updateMinBalance(key, minBalance);
        } catch (AccountException e) {
            logger.warn(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value="/account/{key}/description", method=RequestMethod.PUT)
    public Account updateDescription(@PathVariable("key") String key,
                                            @RequestParam("description") String description) {
        System.out.println(String.format("Description updated for Account: %d, new description: %s", key, description));

        try {
            return accountService.updateDescription(key, description);
        } catch (AccountException e) {
            logger.warn(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value="/transfer", method=RequestMethod.PUT)
    public ResponseEntity<?> transfer(WebRequest request, @RequestBody Transfer transfer) {
        logger.info("Transfer request received. Sending through to accountservice");
        try {
            return accountService.transfer(request, transfer);
        } catch (AccountException e) {
            logger.warn("Failed to update accounts", e.getMessage());
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
