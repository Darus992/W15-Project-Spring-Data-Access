package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final OpinionService opinionService;
    private final PurchaseService purchaseService;
    private final CustomerRepository customerRepository;

    @Transactional
    public void removeAll(){
        opinionService.removeAll();
        purchaseService.removeAll();
        customerRepository.removeAll();
    }
}
