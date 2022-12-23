package daniyal.teast_task.service;

import daniyal.teast_task.models.Currency;
import daniyal.teast_task.models.Limit;
import daniyal.teast_task.models.User;
import daniyal.teast_task.repository.LimitRepository;
import daniyal.teast_task.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
class LimitServiceTest {
    @Autowired
    LimitRepository limitRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    void save() {
        User user = new User(1L, "Test", 123456789);
        Limit limit = new Limit(1L, 1200.0, "1980-01-01", "USD", 1200.0, "service", user);
        userRepository.save(user);
        limitRepository.save(limit);
        assertEquals(limit.getLimitSum(), limitRepository.findByUser_AccountNumber(user.getAccountNumber()).get(0).getLimitSum());
    }

    @Test
    void findByUser_AccountNumber() {
        User user = new User(1L, "Test", 123456789);
        Limit limit = new Limit(1L, 1200.0, "1980-01-01", "USD", 1200.0, "service", user);
        userRepository.save(user);
        limitRepository.save(limit);
        assertEquals(limit.getUser().getAccountNumber(), limitRepository.findByUser_AccountNumber(user.getAccountNumber()).get(0).getUser().getAccountNumber());
    }



    @Test
    void updateLimitLeftById() {
        User user = new User(1L, "Test", 123456789);
        Limit limit = new Limit(1L, 1200.0, "1980-01-01", "USD", 1200.0, "service", user);
        userRepository.save(user);
        limitRepository.save(limit);
        limitRepository.updateLimitLeftById(990, limit.getId());
        System.out.println(limit.getId() + " " + limitRepository.findByUser_AccountNumber(user.getAccountNumber()).get(0).getId());
        assertEquals(limit.getLimitLeft(), limitRepository.findByUser_AccountNumber(user.getAccountNumber()).get(0).getLimitLeft());
    }

    @Test
    public void saveAll() {
        User user = new User(1L, "Test", 123456789);
        Limit limit = new Limit(1L, 1200.0, "1980-01-01", "USD", 1200.0, "service", user);
        Limit limit2 = new Limit(2L, 1300.0, "1980-01-01", "USD", 1300.0, "service", user);
        List<Limit> limits = new ArrayList<>();
        limits.add(limit);
        limits.add(limit2);
        userRepository.save(user);
        limitRepository.saveAll(limits);
        assertEquals(2, limitRepository.findAll().size());
    }
}