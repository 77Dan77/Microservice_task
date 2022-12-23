package daniyal.teast_task.models;

//import org.springframework.data.cassandra.core.cql.Ordering;
//import org.springframework.data.cassandra.core.cql.Ordering;
//import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
//import org.springframework.data.cassandra.core.mapping.Column;
//import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
//import org.springframework.data.cassandra.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

//@Table  //For Cassandra DB
@Entity
@Table(name = "currency")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency {
    public Currency(String symbol, String date, double close) {
        this.symbol = symbol;
        this.date = date;
        this.close = close;
    }

    public Currency(Long id, String currency, double exchange) {
        this.id = id;
        this.symbol = currency;
        this.close = exchange;
    }

    public Currency(Long id, String symbol, String date, double close) {
        this.id = id;
        this.symbol = symbol;
        this.date = date;
        this.close = close;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public Currency() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //    @PrimaryKeyColumn(
//            name = "isbn",
//            ordinal = 2,
//            type = PrimaryKeyType.CLUSTERED)
//    private UUID id;

//    @Column("currency")
    @Column(name = "currency")
    @JsonProperty("symbol")
    private String symbol;

    @Column(name = "date")
    private String date;

//    @PrimaryKeyColumn(
//            name = "exchange", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
//    @Column("exchange")
    @Column(name = "exchange")
    @JsonProperty("close")
    private double close;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getClose() {
        return close;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String currency) {
        this.symbol = currency;
    }

    public double getExchange() {
        return close;
    }

    public void setExchange(double exchange) {
        this.close = exchange;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", currency='" + symbol + '\'' +
                ", exchange=" + close +
                '}';
    }
}
