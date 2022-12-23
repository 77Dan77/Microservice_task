package daniyal.teast_task;


import daniyal.teast_task.controller.CurrencyController;
import daniyal.teast_task.models.Currency;
import daniyal.teast_task.models.Limit;
import daniyal.teast_task.models.Transaction;
//import daniyal.teast_task.service.CurrencyService;
import daniyal.teast_task.models.User;
import daniyal.teast_task.service.CurrencyService;
import daniyal.teast_task.service.LimitService;
import daniyal.teast_task.service.TransactionService;
import daniyal.teast_task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
//import org.springframework.data.cassandra.core.CassandraOperations;
//import org.springframework.data.cassandra.core.CassandraTemplate;
//import org.springframework.data.cassandra.core.query.Criteria;
//import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.UUID;

@Component
public class Runner implements CommandLineRunner {
    TransactionService transactionService;
    UserService userService;
    CurrencyService currencyService;
    CurrencyController currencyController;
    LimitService limitService;

    @Autowired
    public void setLimitService(LimitService limitService) {
        this.limitService = limitService;
    }
    @Autowired
    public void setCurrencyController(CurrencyController currencyController) {
        this.currencyController = currencyController;
    }
    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        OffsetDateTime dateTime = OffsetDateTime.now();
        String stringDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm X"));

//        UUID uuid = UUID.fromString("dan");
//        Currency currency = new Currency("KZT/USD", 467.35);
//        currencyService.save(currency);
//        System.out.println("---- DATA from Cassaandra: " + currencyService.findById(currency.getId()));

        User user = new User(Long.valueOf(1), "Dan", 123456789);
        //User user2 = new User(Long.valueOf(2), "Dan4ik", 777777777, 1000.0, dateTime, "USD", 1000.0);
        Limit limit = new Limit(2L, 1200.0, stringDate, "USD", 1200.0, "service", user);
        Limit limit2 = new Limit(3L, 1000.0, stringDate, "USD", 400.0, "goods", user);
        ArrayList<Limit> limits = new ArrayList<>();
        limits.add(limit);
        limits.add(limit2);
//        limitService.saveAll(limits);

        Currency baseCurrency = new Currency(1L, "KZT/USD", "1980-01-01", 100.0);
        currencyService.save(baseCurrency);
        currencyController.getCurrency();

        Transaction transaction = new Transaction(Long.valueOf(1), 123456789, 023421L, "KZT", 1009.0, "service", stringDate, false, user);
        Transaction transaction2 = new Transaction(Long.valueOf(2), 123456789, 023421L, "KZT", 1054.0, "goods", stringDate, true, user);
        Transaction transaction3 = new Transaction(Long.valueOf(3), 123456789, 023021L, "KZT", 1000.0, "service", stringDate, false, user);
        Transaction transaction4 = new Transaction(Long.valueOf(4), 123456789, 023421L, "KZT", 1230.0, "goods", stringDate, true, user);
        Transaction transaction5 = new Transaction(Long.valueOf(5), 123456789, 023421L, "KZT", 1930.0, "service", stringDate, true, user);
        Transaction transaction6 = new Transaction(Long.valueOf(6), 123456789, 023421L, "KZT", 6230.0, "service", stringDate, false, user);

//        userService.save(user);
//        transactionService.save(transaction);
//        transactionService.save(transaction2);
//        transactionService.save(transaction3);
//        transactionService.save(transaction4);
//        transactionService.save(transaction5);
//        transactionService.save(transaction6);
    }
}
