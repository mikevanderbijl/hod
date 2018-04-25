package nl.trifork.bank.accountms.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Account {

    @Version
    @NotNull
    private long version;
    @Id
    @NotNull
    @GeneratedValue
    private long id;
    @NotNull
    private String key;
    @NotNull
    private long userId;
    private String name;
    private String description;
    private long minBalance;
    private long balance;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Account(String name, String description) {
//        this.key = key;
//        this.userId = userId;
        this.name = name;
        this.description = description;
//        this.minBalance = minBalance;
//        this.balance = balance;
        this.createdAt = new Date();
    }

    protected Account() {
    }

    public long getVersion() { return version; }

    public long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() { return name; }

    public String getDescription() {
        return description;
    }

    public long getBalance() {
        return balance;
    }

    public long getMinBalance() {
        return minBalance;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setKey(String key) { this.key = key; }

    public void setUserId(long userId) { this.userId = userId; }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMinBalance(long minBalance) {
        this.minBalance = minBalance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

}
