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

    private final ProductRepository productRepository;

    @Transactional
    public Product create(Product product) {
        return productRepository.create(product);
    }

    @Transactional
    public void removeAll(){
        productRepository.removeAll();
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product find(String productCode) {
        return productRepository.find(productCode)
                .orElseThrow(() -> new RuntimeException("Product with product code: [%s] is missing".formatted(productCode)));
    }
}