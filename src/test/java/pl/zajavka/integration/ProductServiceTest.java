package pl.zajavka.integration;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.zajavka.business.OpinionService;
import pl.zajavka.business.ProductService;
import pl.zajavka.business.PurchaseService;
import pl.zajavka.business.ReloadDataService;
import pl.zajavka.domain.Opinion;
import pl.zajavka.domain.Purchase;
import pl.zajavka.infrastructure.configuration.ApplicationConfiguration;

import java.util.List;

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceTest {

    private ReloadDataService reloadDataService;
    private PurchaseService purchaseService;
    private OpinionService opinionService;
    private ProductService productService;


    @BeforeEach
    void setUp(){
        Assertions.assertNotNull(reloadDataService);
        Assertions.assertNotNull(purchaseService);
        Assertions.assertNotNull(opinionService);
        Assertions.assertNotNull(productService);
        reloadDataService.reloadData();
    }

    @Test
    @DisplayName("Exercise 10")
    void thatProductIsWiped(){
        //  given
        final var productCode = "68084-618";
        productService.find(productCode);
        List<Opinion> opinionsBefore = opinionService.findAllByProductCode(productCode);
        List<Purchase> purchasesBefore = purchaseService.findAllByProductCode(productCode);

        Assertions.assertEquals(3, opinionsBefore.size());
        Assertions.assertEquals(4, purchasesBefore.size());

        //  when
        productService.removeCompletely(productCode);

        //  then
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> productService.find(productCode));
        Assertions.assertEquals("Product with product code: [%s] is missing".formatted(productCode), exception.getMessage());

        Assertions.assertTrue(opinionService.findAllByProductCode(productCode).isEmpty());
        Assertions.assertTrue(purchaseService.findAllByProductCode(productCode).isEmpty());
    }
}
