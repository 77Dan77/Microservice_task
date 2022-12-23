package daniyal.teast_task.service;

import daniyal.teast_task.models.Transaction;
import daniyal.teast_task.models.User;
import daniyal.teast_task.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
//
@DataJpaTest
@ActiveProfiles("test")
class TransactionServiceTest {
    @Autowired
    TransactionRepository transactionRepository;

    @Test
    void save() {
        User user = new User("Test", 123456789);
        Transaction transaction = new Transaction(123456789, 023421L, "KZT", 1009.0, "service", "1990-32-02", false, user);
        transactionRepository.save(transaction);
        assertEquals(transaction.getSum(), transactionRepository.findById(transaction.getId()).get().getSum());
    }

    @Test
    void delete() {
        User user = new User( "Test", 123456789);
        Transaction transaction = new Transaction( 123456789, 023421L, "KZT", 1009.0, "service", "1990-32-02", false, user);
        transactionRepository.save(transaction);
        assertFalse(transactionRepository.findById(transaction.getId()).isEmpty());
        transactionRepository.deleteById(transaction.getId());
        assertTrue(transactionRepository.findById(transaction.getId()).isEmpty());
    }

    @Test
    void findById() {
        User user = new User( "Test", 123456789);
        Transaction transaction = new Transaction(123456789, 023421L, "KZT", 1009.0, "service", "1990-32-02", false, user);
        transactionRepository.save(transaction);
        assertEquals(transaction.getSum(), transactionRepository.findById(transaction.getId()).get().getSum());
    }

    @Test
    void findByLimitExceeded() {
        User user = new User( "Test", 123456789);
        Transaction transaction = new Transaction( 123456789, 023421L, "KZT", 1009.0, "service", "1990-32-02", false, user);
        transactionRepository.save(transaction);
        assertEquals(transaction.isLimitExceeded(), transactionRepository.findByLimitExceeded(transaction.isLimitExceeded()).get(0).isLimitExceeded());
    }

    @Test
    void findByAccountTo() {
        User user = new User("Test", 123456789);
        Transaction transaction = new Transaction( 123456789, 023421L, "KZT", 1009.0, "service", "1990-32-02", false, user);
        transactionRepository.save(transaction);
        assertEquals(transaction.getAccountTo(), transactionRepository.findByAccountTo(transaction.getAccountTo()).get(0).getAccountTo());

    }

    @Test
    void findByDate() {
        User user = new User( "Test", 123456789);
        Transaction transaction = new Transaction(123456789, 023421L, "KZT", 1009.0, "service", "1990-32-02", false, user);
        transactionRepository.save(transaction);
        assertEquals(transaction.getDate(), transactionRepository.findByDate(transaction.getDate()).get(0).getDate());

    }


    @Test
    void findBySum() {
        User user = new User( "Test", 123456789);
        Transaction transaction = new Transaction(123456789, 023421L, "KZT", 1009.0, "service", "1990-32-02", false, user);
        transactionRepository.save(transaction);
        assertEquals(transaction.getSum(), transactionRepository.findBySum(transaction.getSum()).getSum());
    }

}