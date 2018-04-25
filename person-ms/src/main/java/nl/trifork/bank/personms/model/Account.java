package nl.trifork.bank.personms.model;

import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
public class Account {
    private long id;
    private String key;
    private String name;
    private long userId;
    private String description;
    private long minBalance;
    private long balance;
    private Date createdAt;
    private long version;

    public Account() {
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() { return name;}
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(long minBalance) {
        this.minBalance = minBalance;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
