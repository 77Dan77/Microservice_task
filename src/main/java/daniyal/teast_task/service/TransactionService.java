package daniyal.teast_task.service;

import daniyal.teast_task.models.Transaction;
import daniyal.teast_task.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    //----------------------------------------------------------
    public List<Transaction> findAll(){
        return transactionRepository.findAll();
    }

    @Transactional
    public <S extends Transaction> S save(S entity){
        return transactionRepository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }

    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> findByLimitExceeded(boolean limitExceeded) {
        return transactionRepository.findByLimitExceeded(limitExceeded);
    }

    public List<Transaction> findByAccountTo(long accountTo){
        return transactionRepository.findByAccountTo(accountTo);
    }

    public List<Transaction> findByDate(String date){
        return transactionRepository.findByDate(date);
    }

    public List<Transaction> findByExpense_category(String expense_category){
        return transactionRepository.findByExpense_category(expense_category);
    }

    public Transaction findBySum(double sum){
        return findBySum(sum);
    }

    public List<Transaction> findByUser_AccountNumber(long accountNumber){
        return transactionRepository.findByUser_AccountNumber(accountNumber);
    }

    public List<Transaction> findByUser_Transactions_LimitExceeded(boolean limitExceeded, long accountNumber){
        return transactionRepository.findByUser_Transactions_LimitExceeded(limitExceeded, accountNumber);
    }

}
