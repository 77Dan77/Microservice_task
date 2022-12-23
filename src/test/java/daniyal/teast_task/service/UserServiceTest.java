package daniyal.teast_task.service;

import daniyal.teast_task.models.Transaction;
import daniyal.teast_task.models.User;
import daniyal.teast_task.repository.LimitRepository;
import daniyal.teast_task.repository.TransactionRepository;
import daniyal.teast_task.repository.UserRepository;
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
class UserServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    LimitRepository limitRepository;

    @Test
    void save() {
        User user = new User( 1L, "Test", 123456789);
        userRepository.save(user);
        System.out.println(userRepository.findAll().toString());
        assertEquals(user.getFullname(), userRepository.findByFullname(user.getFullname()).getFullname());
    }

    @Test
    void findAll() {
        User user = new User( "Test3", 123456789);
        User user2 = new User( "Test2", 123456789);
        userRepository.save(user);
        userRepository.save(user2);
        List<User> userList = userRepository.findAll();
        System.out.println(userList.toString());
        assertEquals(2, userList.size());
    }

    @Test
    void deleteById() {
        User user = new User( "Test5", 123456789);
        userRepository.save(user);
        assertFalse(userRepository.findById(user.getId()).isEmpty());
        userRepository.deleteById(user.getId());
        assertTrue(userRepository.findById(user.getId()).isEmpty());
    }

    @Test
    void findByAccountNumber() {
        User user = new User(1L, "Test4", 123456789);
        userRepository.save(user);
        assertEquals(user.getAccountNumber(), userRepository.findByAccountNumber(user.getAccountNumber()).getAccountNumber());
    }

    @Test
    void findByFullname() {
        User user = new User(1L, "Tes6t", 123456789);
        userRepository.save(user);
        assertEquals(user.getFullname(), userRepository.findByFullname(user.getFullname()).getFullname());
    }

}