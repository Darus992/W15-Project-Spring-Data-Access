package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.domain.Opinion;
import pl.zajavka.domain.Product;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private OpinionService opinionService;
    private PurchaseService purchaseService;
    private final ProductRepository productRepository;

    @Transactional
    public Product create(Product product) {
        return productRepository.create(product);
    }

    public Product find(String productCode) {
        return productRepository.find(productCode)
                .orElseThrow(() -> new RuntimeException("Product with product code: [%s] is missing".formatted(productCode)));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public void removeAll(){
        productRepository.removeAll();
    }

    @Transactional
    public void removeCompletely(String productCode) {
        purchaseService.removeAllByProductCode(productCode);
        opinionService.removeAllByProductCode(productCode);
        productRepository.remove(productCode);
    }
}