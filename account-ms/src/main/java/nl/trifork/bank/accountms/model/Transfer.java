package nl.trifork.bank.accountms.model;

import java.util.Date;

public class Transfer {
    private long id;
    private long amount;
    private long issuer;
    private String from_key;
    private String to_key;
    private Date createdAt;

    protected Transfer() {
    }

    public Transfer(Long issuer, Long amount, String from_Key, String to_key) {
        this.issuer = issuer;
        this.amount = amount;
        this.from_key = from_Key;
        this.to_key = to_key;
    }

    public long getid() {
        return id;
    }

    public long getIssuer() {
        return issuer;
    }

    public long getAmount() {
        return amount;
    }

    public String getFrom_key() {
        return from_key;
    }

    public String getTo_key() {
        return to_key;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
