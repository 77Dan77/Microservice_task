package daniyal.teast_task.repository;
//
import daniyal.teast_task.models.Currency;
//import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
//
import java.util.List;
import java.util.Optional;
import java.util.UUID;
//
@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    @Override
    <S extends Currency> S save(S entity);

    @Transactional
    @Modifying
    @Query("update Currency c set c.close = ?1, c.date = ?2 where c.id = ?3")
    int updateCloseById(double close, String date, Long id);


    @Override
    List<Currency> findAll();

    @Override
    Optional<Currency> findById(Long id);

    @Override
    <S extends Currency> List<S> findAll(Example<S> example);

    @Override
    void deleteById(Long id);
}
