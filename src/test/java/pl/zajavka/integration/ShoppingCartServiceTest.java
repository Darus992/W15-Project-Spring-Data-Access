package pl.zajavka.integration;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.zajavka.business.*;
import pl.zajavka.domain.*;
import pl.zajavka.infrastructure.configuration.ApplicationConfiguration;

import java.util.List;

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShoppingCartServiceTest {

    private ReloadDataService reloadDataService;
    private ShoppingCartService shoppingCartService;
    private CustomerService customerService;
    private PurchaseService purchaseService;
    private OpinionService opinionService;
    private ProducerService producerService;
    private ProductService productService;

    @BeforeEach
    public void setUp(){
        Assertions.assertNotNull(reloadDataService);
        Assertions.assertNotNull(shoppingCartService);
        Assertions.assertNotNull(customerService);
        Assertions.assertNotNull(purchaseService);
        Assertions.assertNotNull(opinionService);
        Assertions.assertNotNull(producerService);
        Assertions.assertNotNull(productService);
        reloadDataService.reloadData();
    }

    @Test
    @DisplayName("Exercise 9")
    void thatProductCanBeBoughtByCustomer(){
        //  given
        final Customer customer = customerService.create(StoreFixtures.someCustomer());
        final Producer producer = producerService.create(StoreFixtures.someProducer());
        final Product product = productService.create(StoreFixtures.someProduct1(producer));
        productService.create(StoreFixtures.someProduct2(producer));

        final List<Purchase> before = purchaseService.findAll(customer.getEmail(), product.getProductCode());

        //  when
        shoppingCartService.makeAPurchase(customer.getEmail(), product.getProductCode(), 10);

        //  then
        final List<Purchase> after = purchaseService.findAll(customer.getEmail(), product.getProductCode());
        Assertions.assertEquals(before.size() + 1, after.size());
    }
}
