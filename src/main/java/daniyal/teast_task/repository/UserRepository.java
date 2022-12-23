package daniyal.teast_task.repository;

import daniyal.teast_task.models.Transaction;
import daniyal.teast_task.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Create user
    @Override
    <S extends User> S save(S entity);

    @Transactional
    @Modifying
    @Query("update User u set u.transactions = ?1 where u.accountNumber = ?2")
    int updateTransactionsByAccountNumber(Transaction transactions, long accountNumber);

    @Override
    List<User> findAll();

    //Delete user by his id
    @Override
    void deleteById(Long aLong);

    //Find user's transactions according exceeding of its limit
    @Query("select u from User u inner join Transaction transactions where transactions.limitExceeded = ?1")
    List<User> findByTransactions_LimitExceeded(boolean limitExceeded);

    //Find user by his account number
    @Query("select u from User u where u.accountNumber = ?1")
    User findByAccountNumber(long accountNumber);

    //Find user by his fullname
    @Query("select u from User u where u.fullname = ?1")
    User findByFullname(String fullname);

//    //Find user by his limit date
//    @Query("select u from User u where u.limitDate = ?1")
//    User findByLimitDate(OffsetDateTime limitDate);

    //Find user by his transaction date
    @Query("select u from User u inner join u.transactions transactions where transactions.date = ?1")
    User findByTransactions_Date(OffsetDateTime date);

//    //Update user's limit
//    @Transactional
//    @Modifying
//    @Query("update User u set u.limitSum = ?1 where u.accountNumber = ?2")
//    int updateLimitSumByAccountNumber(double limitSum, long accountNumber);
//
//    @Transactional
//    @Modifying
//    @Query("update User u set u.limitSum = ?1, u.limitDate = ?2 where u.accountNumber = ?3")
//    int updateLimitSumAndLimitDateByAccountNumber(double limitSum, OffsetDateTime limitDate, long accountNumber);



}
