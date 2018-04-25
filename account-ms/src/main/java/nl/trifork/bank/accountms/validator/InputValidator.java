package nl.trifork.bank.accountms.validator;

import nl.trifork.bank.accountms.DAO.AccountDAO;
import nl.trifork.bank.accountms.exception.AccountException;
import nl.trifork.bank.accountms.model.Account;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class InputValidator {


    @Autowired
    private AccountDAO accountDAO;

    protected static boolean validateKey(String key) { return String.valueOf(key).length() == 9; }

    protected static boolean validateUserId(long userId) { return String.valueOf(userId).length() > 0; }

    protected static boolean validateLong(long inputLong) { return inputLong < Long.MAX_VALUE && inputLong > Long.MIN_VALUE; }

    protected static boolean validateString(String string) { return string.length() > 0; }

    protected boolean issuerOwnsAccount(long issuer, String key) {
        List<Account> userAccounts = accountDAO.findAccountsByUserId(issuer);
        for (Account account : userAccounts) {
            if (account.getKey() == key) {
                return true;
            }
        }
        return false;
    }

    protected boolean accountExists(String key) {
        return accountDAO.findAccountByKey(key) != null;
    }

    protected boolean accountHasValidBalance(String key, long amount) {
        Account account = accountDAO.findAccountByKey(key);
        long availableBalance = (account.getBalance() - account.getMinBalance());

        return availableBalance >= amount;
    }

    protected boolean balanceAboveMinBalanceByKey(String key) {
        Account account = accountDAO.findAccountByKey(key);

        long balance    = account.getBalance();
        long minBalance = account.getMinBalance();

        return balance >= minBalance;
    }
}
