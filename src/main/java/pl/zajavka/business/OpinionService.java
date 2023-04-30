package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.domain.Opinion;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class OpinionService {

    private final OpinionRepository opinionRepository;

    @Transactional
    public Opinion create(Opinion opinion) {
        return opinionRepository.create(opinion);
    }

    @Transactional
    public void removeAll() {
        opinionRepository.removeAll();
    }

    @Transactional
    public void removeAll(String email) {
        opinionRepository.remove(email);
    }

    public List<Opinion> findAll(String email) {
        return opinionRepository.findAll(email);
    }
}
