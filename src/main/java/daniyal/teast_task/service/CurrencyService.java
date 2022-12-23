package daniyal.teast_task.service;

import daniyal.teast_task.models.Currency;
import daniyal.teast_task.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {
    CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public <S extends Currency> S save(S entity){
        return currencyRepository.save(entity);
    }

    public Optional<Currency> findById(Long id){
        return currencyRepository.findById(id);
    }

    public void deleteById(Long id){
       currencyRepository.deleteById(id);
    }

    public List<Currency> findAll(){
        return currencyRepository.findAll();
    }

    public int updateCloseById(double close, String date, Long id){
        return currencyRepository.updateCloseById(close, date, id);
    }
}
