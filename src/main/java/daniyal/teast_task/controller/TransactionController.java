package daniyal.teast_task.controller;

import daniyal.teast_task.models.Currency;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController("TransactionController")
@RequestMapping(value = "/api/transactions/")
public class TransactionController {
    TransactionService transactionService;
    UserService userService;
    CurrencyService currencyService;
    LimitService limitService;
    CurrencyController currencyController;
    @Autowired
    public void setCurrencyController(CurrencyController currencyController) {
        this.currencyController = currencyController;
    }
    @Autowired
    public void setLimitService(LimitService limitService) {
        this.limitService = limitService;
    }
    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    Logger log = LoggerFactory.getLogger(this.getClass());

    ////----------------------------------------------------------------------------------------------

    @GetMapping("/getTransactionById/{id}") //*Optional* get transaction by its id
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Optional<Transaction>> getTransaction(@PathVariable(name = "id") Long id) {
        transactionService.findById(id);
        return new ResponseEntity<>(transactionService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/createTransaction/{accNumber}") //Add transaction to user account (imitation that concrete user bought smth)
    @ResponseStatus(HttpStatus.OK)
    public String createTransaction(@RequestBody Transaction transaction, @PathVariable(name = "accNumber") long accNum) throws InterruptedException {
        OffsetDateTime dateTime = OffsetDateTime.now();
        String stringDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm X"));

       if (userService.findByAccountNumber(accNum) != null) {
           if (currencyService.findById(1L).get().getDate().equals("1980-01-01")){ //Getting current currency rate from external API
               System.out.println("*************************Get API*********************************");
               currencyController.getCurrency();
               Thread.sleep(10000);
           }

           boolean limitExcFlag = false;
           User user = userService.findByAccountNumber(accNum);
           List<Limit> limits = user.getLimits();
           DecimalFormat numberFormat = new DecimalFormat("#.##");

           double convertedSum = transaction.getSum();
           if (transaction.getCurrencyShortname().equals("KZT")){  /// Converting KZT to USD according valid rate if it needs
                Currency currency = currencyService.findById(1L).get();
                convertedSum = transaction.getSum()/currency.getClose();
                String s = numberFormat.format(convertedSum);
                s = s.replaceAll(",",".");
                convertedSum = Double.parseDouble(s);
           }

           for (int i = limits.size() - 1; i >= 0; i--) {  //Changing limit-Left of user limit according transaction sum
               try {
                   if (limits.get(i).getExpense_category().equals(transaction.getExpense_category())) {
                       Limit limit = limits.get(i);

                       if (limit.getLimitLeft()-convertedSum < 0){ //Setting limitExceeded flag to "true" if limit is exceeded:)
                           limitExcFlag = true;

                       }
                       double limLeft = limit.getLimitLeft()-convertedSum;
                       String s = numberFormat.format(limLeft);
                       s = s.replaceAll(",",".");
                       limLeft = Double.parseDouble(s);
                       //Saving new limit-left value for particular user limit to db
                       limitService.updateLimitLeftById(limLeft, limit.getId());
                       break;
                   }
               } catch (Exception e) {
                       log.warn("User havent this limits");
                       e.printStackTrace();
               }
           }
            //Saving new transaction
           Transaction newTransaction = new Transaction(Long.valueOf(99), user.getAccountNumber(), transaction.getAccountTo(), transaction.getCurrencyShortname(), transaction.getSum(), transaction.getExpense_category(), stringDate, limitExcFlag, user);
           transactionService.save(newTransaction);
           return "\"" + " transaction " + "\" has been added!" + newTransaction;
       }else{
           System.out.println("__NO SUCH ACCOUNT!__");
           return "\"" + " transaction NOT " + "\" added!";
       }
    }

    @DeleteMapping("/deleteTransaction/{id}")//*Optional* delete transaction by its id
    public String deleteBook(@PathVariable(name = "id") Long id) {
        if (transactionService.findById(id).isEmpty()) return "Exception: There is no transaction with this ID!!!";
        else {
            transactionService.delete(id);
            return "Transaction has been deleted!";
        }
    }

}
