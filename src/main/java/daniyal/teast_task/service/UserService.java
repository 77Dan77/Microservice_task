package daniyal.teast_task.service;

import daniyal.teast_task.models.User;
import daniyal.teast_task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //-----------------------------------------------------------

    @Transactional
    public <S extends User> S save(S entity){
        return userRepository.save(entity);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public List<User> findByTransactions_LimitExceeded(boolean limitExceeded){
        return userRepository.findByTransactions_LimitExceeded(limitExceeded);
    }

    public User findByAccountNumber(long accountNumber){
        return userRepository.findByAccountNumber(accountNumber);
    }

    public User findByFullname(String fullname){
        return userRepository.findByFullname(fullname);
    }

    public User findByTransactions_Date(OffsetDateTime date){
        return userRepository.findByTransactions_Date(date);
    }

}
