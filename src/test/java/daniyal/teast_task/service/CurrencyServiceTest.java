package daniyal.teast_task.service;

import daniyal.teast_task.models.Currency;
import daniyal.teast_task.repository.CurrencyRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
//@SpringBootTest()
@DataJpaTest
@ActiveProfiles("test")
class CurrencyServiceTest {
    @Autowired
    CurrencyRepository currencyRepository;

//    @Before
//    public void setUp() throws Exception {
//        Currency baseCurrency = new Currency(1L, "KZT/USD", "1980-01-01", 100.0);
//        currencyRepository.save(baseCurrency);
//    }

    @Test
    void save() {
          Currency baseCurrency = new Currency(1L, "KZT/USD", "1980-01-01", 100.0);
          currencyRepository.save(baseCurrency);
          List<Currency> currency2 = currencyRepository.findAll();
          assertEquals(baseCurrency.getDate(), currency2.get(0).getDate());
    }

    @Test
    void findById() {
        Currency baseCurrency = new Currency(1L, "KZT/USD", "1980-01-01", 100.0);
        currencyRepository.save(baseCurrency);
        List<Currency> currency2 = currencyRepository.findAll();
        assertEquals(baseCurrency.getDate(), currency2.get(0).getDate());
    }

    @Test
    void deleteById() {
        Currency baseCurrency = new Currency( "KZT/USD", "1980-01-01", 100.0);
        currencyRepository.save(baseCurrency);
        List<Currency> currency2 = currencyRepository.findAll();
        assertEquals(1, currency2.size());
        currencyRepository.deleteById(baseCurrency.getId());
        currency2 = currencyRepository.findAll();
        assertEquals(0, currency2.size());
    }

    @Test
    void findAll() {
        Currency baseCurrency = new Currency(1L, "KZT/USD", "1980-01-01", 100.0);
        currencyRepository.save(baseCurrency);
        List<Currency> currencies = currencyRepository.findAll();
        assertEquals(1, currencies.size());
    }

}