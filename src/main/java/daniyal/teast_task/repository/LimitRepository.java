package daniyal.teast_task.repository;

import daniyal.teast_task.models.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface LimitRepository extends JpaRepository<Limit, Long> {
    @Override
    <S extends Limit> S save(S entity);

    @Transactional
    @Modifying
    @Query("update Limit l set l.limitLeft = ?1 where l.user.accountNumber = ?2")
    int updateLimitLeftByUser_AccountNumber(double limitLeft, long accountNumber);

    @Transactional
    @Modifying
    @Query("update Limit l set l.limitLeft = ?1 where l.id = ?2")
    int updateLimitLeftById(double limitLeft, Long id);

    @Override
    <S extends Limit> List<S> saveAll(Iterable<S> entities);

    @Transactional
    @Modifying
    @Query("update Limit l set l.limitSum = ?1, l.limitDate = ?2 where l.user.accountNumber = ?3")
    int updateLimitSumAndLimitDateByUser_AccountNumber(double limitSum, OffsetDateTime limitDate, long accountNumber);

    @Transactional
    @Modifying
    @Query("update Limit l set l.user.fullname = ?1 where l.user.accountNumber = ?2")
    int updateUserFullnameByUser_AccountNumber(String fullname, long accountNumber);



    @Query("select l from Limit l where l.user.accountNumber = ?1")
    List<Limit> findByUser_AccountNumber(long accountNumber);



}
