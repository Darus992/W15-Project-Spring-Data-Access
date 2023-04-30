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
