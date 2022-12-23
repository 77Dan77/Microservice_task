package daniyal.teast_task.controller;


import com.google.common.collect.Iterables;
import daniyal.teast_task.models.Currency;
import daniyal.teast_task.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;


@RestController("CurrencyController")
@RequestMapping(value = "/currency")
public class CurrencyController {
    CurrencyService currencyService;
    final String uri = "https://api.twelvedata.com/time_series?symbol=USD/KZT&interval=1day&apikey=5a1afa596d7b4734a907852a610d0470";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/getCurrency")  //Getting current exchange rate from external API
    public Currency getCurrency() {
        String url = uri;
        RestTemplate restTemplate = new RestTemplate();
        Object object = restTemplate.getForObject(url, Object.class); //Впорос, возникший по ходу решения: как действительно правильно принимать данные json формата из сторонних апи?
                                                                      // создать собственный класс маппер, полностью повторяющий структуру json ответа?
        String strObj = object.toString();
        String curr = strObj.substring (14, 21);
        System.out.println("-----------------" + curr);

        String[] strings = object.toString().split(",", 15);
        for (int i = 0; i < strings.length; i++){
            System.out.println(i + " srting: \"" + strings[i] + "\"");
        }

        String exchange = strings[9].strip().substring(6, 12);
        String date = strings[5].strip().substring(18, strings[5].length()-1);
        String date2 = strings[10].strip().substring(10, strings[10].length()-1);
        Currency currency = null;
        try {
            currency = new Currency(1L, curr, date, Double.parseDouble(exchange));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            log.warn("Cannot parse string to double, make sure that your string is valid");
            log.info("Using previous exhange close value...");

            try {
                exchange = strings[14].strip().substring(6, 12);   //Для предыдущего курса закрытыия, если нет текущего
                System.out.println("===============" + exchange);     //НЕ забыть протестить!
                currency = new Currency(1L, curr, date2, Double.parseDouble(exchange));
            }catch (NumberFormatException ee){
                log.warn("Cannnot get current currency rate, Please check \"CurrencyController.java\"");
            }
        }///This shit works. можно не исправлять

        List<Currency> currencies = currencyService.findAll();
        Currency currency1 = null;
        try {
            currency1 = Iterables.getLast(currencies);  //Get the last currency from db (optional)
        } catch (Exception e) {
            System.out.println("---There is not currency in database---");
            e.printStackTrace();
        }

        System.out.println(currency);
        currencyService.updateCloseById(currency.getClose(), currency.getDate(), currency1.getId());
        return currencyService.findById(currency1.getId()).get();
    }
}
