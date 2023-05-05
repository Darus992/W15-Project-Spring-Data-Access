package pl.zajavka.business;

import pl.zajavka.domain.Opinion;

import java.util.List;

public interface OpinionRepository {
    Opinion create(Opinion opinion);

    List<Opinion> findAll();

    List<Opinion> findAll(String email);

    List<Opinion> findAllByProductCode(String productCode);

    List<Opinion> findUnwantedOpinions();

    void removeAll();

    void remove(String email);

    void removeAllByProductCode(String productCode);

    void removeUnwantedOpinions();

    boolean customerGivesUnwantedOpinions(String email);
}
