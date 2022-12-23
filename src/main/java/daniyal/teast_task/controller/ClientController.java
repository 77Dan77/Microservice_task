package daniyal.teast_task.controller;

import daniyal.teast_task.models.Limit;
import daniyal.teast_task.models.Transaction;
import daniyal.teast_task.models.User;
import daniyal.teast_task.service.CurrencyService;
import daniyal.teast_task.service.LimitService;
import daniyal.teast_task.service.TransactionService;
import daniyal.teast_task.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController("ClientController")
@RequestMapping(value = "/api/client/")
public class ClientController {
    UserService userService;
    TransactionService transactionService;
    CurrencyService currencyService;
    LimitService limitService;

    @Autowired
    public void setLimitService(LimitService limitService) {
        this.limitService = limitService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    Logger log = LoggerFactory.getLogger(this.getClass());

    //------------------------------------------------------------------------------------------

    @GetMapping("/getLimitExceededTransactions/{accNumber}") //Get transactions with exceeded limit of particular user
    @ResponseStatus(HttpStatus.OK)
    public Collection<Transaction> getTransaction(@PathVariable(name = "accNumber") long accNum) {
        List<Transaction> transactions = transactionService.findByUser_Transactions_LimitExceeded(true, accNum);
        if (!transactions.isEmpty()){

            return transactions;
        }else {
            log.warn("User hasn't transactions that exceeded the limit");
            return transactions;
        }
    }

    @PutMapping("/updateLimit/{accNumber}/{excCat}") //Get transactions with exceeded limit of particular user
    @ResponseStatus(HttpStatus.OK)
    public String updateLimit(@PathVariable(name = "accNumber") long accNum, @RequestBody double newLimit, @PathVariable(name = "excCat") String excCat) {
        if (userService.findByAccountNumber(accNum) != null) {
            OffsetDateTime dateTime = OffsetDateTime.now();
            String stringDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm X"));

            User user = userService.findByAccountNumber(accNum);
            List<Limit> limits = user.getLimits();
            Limit limit = null;
            int flag = 0;
            for (int i = limits.size() - 1; i >= 0; i--) {
                try {       //Check last added user limits according their exceeded category
                    if (limits.get(i).getExpense_category().equals(excCat)) {
                        if (newLimit > limits.get(i).getLimitSum()) {
                            flag = i;
                            limit = new Limit(99L, newLimit, stringDate, "USD", (newLimit - limits.get(i).getLimitSum()) + limits.get(i).getLimitLeft(), excCat, user);
                        } else {
                            flag = i;
                            System.out.println("?????????" + (newLimit - limits.get(i).getLimitLeft()));
                            limit = new Limit(99L, newLimit, stringDate, "USD", (newLimit - limits.get(i).getLimitLeft()), excCat, user);
                        }
                        break; ////
                    }
                } catch (Exception e) {
                    log.warn("User havent this limits");
                    e.printStackTrace();
                }
            }

            limitService.save(limit);
            return " Limit of " + user.getFullname() + " changed from " + user.getLimits().get(flag).getLimitSum() + " to " + newLimit + " " + user.getLimits().get(flag).getLimitCurrencyShortname() + ", and new date is " + OffsetDateTime.now();
        }else{
            System.out.println("__NO SUCH ACCOUNT!__");
            return "\"" + " Limit NOT " + "\" updated! There is no user with such account number";
        }
    }

    @GetMapping("/getLimit/{accNumber}") //Get
    @ResponseStatus(HttpStatus.OK)
    public Collection<Limit> getLimit(@PathVariable(name = "accNumber") long accNum) {
        Collection<Limit> limits = new ArrayList<>();
        limits = limitService.findByUser_AccountNumber(accNum);
        return limits;
    }


}
