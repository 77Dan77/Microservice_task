package daniyal.teast_task.models;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "limits")
public class Limit {
    public Limit(Long id, double limitSum, String limitDate, String limitCurrencyShortname, double limitLeft, String expense_category, User user) {
        this.id = id;
        this.limitSum = limitSum;
        this.limitDate = limitDate;
        this.limitCurrencyShortname = limitCurrencyShortname;
        this.limitLeft = limitLeft;
        this.expense_category = expense_category;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private double limitSum = 0;
    private String limitDate;
    private String limitCurrencyShortname;
    private double limitLeft;
    private String expense_category;

    @Column(name = "expense_category")
    public String getExpense_category() {
        return expense_category;
    }

    public void setExpense_category(String expense_category) {
        this.expense_category = expense_category;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName="id")
    private User user;

    @Column(name = "limit_sum")
    public double getLimitSum() {
        return limitSum;
    }

    public void setLimitSum(double limitSum) {
        this.limitSum = limitSum;
    }

    @Column(name = "limit_date")
    public String getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(String limitDate) {
        this.limitDate = limitDate;
    }

    @Column(name = "currency_shortname")
    public String getLimitCurrencyShortname() {
        return limitCurrencyShortname;
    }

    public void setLimitCurrencyShortname(String limitCurrencyShortname) {
        this.limitCurrencyShortname = limitCurrencyShortname;
    }

    @Column(name = "limit_left")
    public double getLimitLeft() {
        return limitLeft;
    }

    public void setLimitLeft(double limitLeft) {
        this.limitLeft = limitLeft;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
