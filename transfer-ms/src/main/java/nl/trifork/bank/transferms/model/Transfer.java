package nl.trifork.bank.transferms.model;


import javax.persistence.*;
import java.util.Date;

/**
 * Transfer entity Class.
 */
@Entity
public class Transfer {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private long amount;
    private String from_key;
    private String to_key;
    private long userId;
    private String description;
    @Column(updatable = false)
    private Date createdAt;


    protected Transfer() {
    }

    public Transfer(long amount, String from_key, String to_key, long userId, String description, Date createdAt) {
        this.amount = amount;
        this.from_key = from_key;
        this.to_key = to_key;
        this.userId = userId;
        this.description = description;
        this.createdAt = createdAt;
    }
    /*Maak constructor met value setters. */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFrom_key() {
        return from_key;
    }

    public void setFrom_key(String from_key) {
        this.from_key = from_key;
    }

    public String getTo_key() {
        return to_key;
    }

    public void setTo_key(String to_key) {
        this.to_key = to_key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
