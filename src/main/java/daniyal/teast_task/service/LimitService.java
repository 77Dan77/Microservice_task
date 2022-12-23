package daniyal.teast_task.service;

import daniyal.teast_task.models.Limit;
import daniyal.teast_task.repository.LimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LimitService {
    LimitRepository limitRepository;

    @Autowired
    public LimitService(LimitRepository limitRepository) {
        this.limitRepository = limitRepository;
    }

    public <S extends Limit> S save(S entity){
        return limitRepository.save(entity);
    }


    public List<Limit> findByUser_AccountNumber(long accountNumber){
        return limitRepository.findByUser_AccountNumber(accountNumber);
    }

    public <S extends Limit> List<S> saveAll(Iterable<S> entities){
        return limitRepository.saveAll(entities);
    }

    public int updateLimitLeftById(double limitLeft, Long id){
        return  limitRepository.updateLimitLeftById(limitLeft, id);
    }

}
