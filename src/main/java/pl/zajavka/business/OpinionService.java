package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.domain.Opinion;
import pl.zajavka.domain.Purchase;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class OpinionService {

    private final PurchaseService purchaseService;
    private final OpinionRepository opinionRepository;

    @Transactional
    public Opinion create(Opinion opinion) {
        List<Purchase> purchases = purchaseService.findAll(opinion.getCustomer().getEmail(), opinion.getProduct().getProductCode());

        if(purchases.isEmpty()){
            throw new RuntimeException("Customer: [%s] wants to give opinion for product: [%s] but there is no purchase"
                    .formatted(opinion.getCustomer().getEmail(), opinion.getProduct().getProductCode()));
        }
        return opinionRepository.create(opinion);
    }

    public List<Opinion> findAll() {
        return opinionRepository.findAll();
    }

    public List<Opinion> findAll(String email) {
        return opinionRepository.findAll(email);
    }

    public List<Opinion> findAllByProductCode(String productCode) {
        return opinionRepository.findAllByProductCode(productCode);
    }

    public List<Opinion> findUnwantedOpinions() {
        return opinionRepository.findUnwantedOpinions();
    }

    @Transactional
    public void removeAll() {
        opinionRepository.removeAll();
    }

    @Transactional
    public void removeAll(String email) {
        opinionRepository.remove(email);
    }

    public void removeAllByProductCode(String productCode) {
        opinionRepository.removeAllByProductCode(productCode);
    }

    @Transactional
    public void removeUnwantedOpinions() {
        opinionRepository.removeUnwantedOpinions();
    }

    public boolean customerGivesUnwantedOpinions(String email) {
        return opinionRepository.customerGivesUnwantedOpinions(email);
    }
}
