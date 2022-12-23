package daniyal.teast_task.repository;

import daniyal.teast_task.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    //Create transaction
    @Override
    <S extends Transaction> S save(S entity);

    @Override
    List<Transaction> findAll();

    //Delete transaction(for admin only)
    @Override
    void deleteById(Long aLong);

    //Find transactions according limit exceeding
    @Query("select t from Transaction t where t.limitExceeded = ?1")
    List<Transaction> findByLimitExceeded(boolean limitExceeded);

    @Override
    Optional<Transaction> findById(Long aLong);

    //Find transactions done FROM one account
    @Query("select t from Transaction t where t.accountFrom = ?1")
    List<Transaction> findByAccountFrom(long accountFrom);

    //Find transactions done TO one account
    @Query("select t from Transaction t where t.accountTo = ?1")
    List<Transaction> findByAccountTo(long accountTo);

    //Find transactions done at particular date
    @Query("select t from Transaction t where t.date = ?1")
    List<Transaction> findByDate(String date);

    //Find transactions according their expense category
    @Query("select t from Transaction t where t.expense_category = ?1")
    List<Transaction> findByExpense_category(String expense_category);

    //Find transaction of concrete sum
    @Query("select t from Transaction t where t.sum = ?1")
    Transaction findBySum(double sum);

    //Find all tansactions of concrete account
    @Query("select t from Transaction t where t.user.accountNumber = ?1")
    List<Transaction> findByUser_AccountNumber(long accountNumber);

    //Find transactions of particular user at concrete date
    @Query("select t from Transaction t inner join t.user.transactions transactions " +
            "where t.user.accountNumber = ?1 and transactions.date = ?2")
    List<Transaction> findByUser_AccountNumberAndUser_Transactions_Date(long accountNumber, OffsetDateTime date);

    //Find transactions of concrete account which has exceeded the limit
    @Query("select t from Transaction t " +
            "where t.limitExceeded = ?1 and t.user.accountNumber = ?2")
    List<Transaction> findByUser_Transactions_LimitExceeded(boolean limitExceeded, long accountNumber);

    @Query("select t from Transaction t where t.limitExceeded = ?1")
    List<Transaction> ex(boolean limitExceeded);



}
