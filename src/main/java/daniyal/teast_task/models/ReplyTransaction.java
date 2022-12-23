package daniyal.teast_task.models;

import java.time.OffsetDateTime;

public class ReplyTransaction {
    private Transaction transaction;
    private double limitSum;
    private OffsetDateTime date;
    private String limitCurrencyShortname;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public double getLimitSum() {
        return limitSum;
    }

    public void setLimitSum(double limitSum) {
        this.limitSum = limitSum;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public String getLimitCurrencyShortname() {
        return limitCurrencyShortname;
    }

    public void setLimitCurrencyShortname(String limitCurrencyShortname) {
        this.limitCurrencyShortname = limitCurrencyShortname;
    }
}
