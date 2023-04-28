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

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerServiceTest {

    private ReloadDataService reloadDataService;
    private CustomerService customerService;
    private PurchaseService purchaseService;
    private OpinionService opinionService;
    private ProducerService producerService;
    private ProductService productService;

    @BeforeEach
    public void setUp(){
        Assertions.assertNotNull(reloadDataService);
        Assertions.assertNotNull(customerService);
        Assertions.assertNotNull(purchaseService);
        Assertions.assertNotNull(opinionService);
        Assertions.assertNotNull(producerService);
        Assertions.assertNotNull(productService);
        reloadDataService.loadRandomData();
    }

    @Test
    @DisplayName("Exercise 4 part 1")
    void thatCustomerRemovedCorrectly(){
        //  given
        final Customer customer = customerService.create(StoreFixtures.someCustomer());
        final Producer producer = producerService.create(StoreFixtures.someProducer());
        final Product product1 = productService.create(StoreFixtures.someProduct1(producer));
        final Product product2 = productService.create(StoreFixtures.someProduct2(producer));
        final Purchase purchase1 = purchaseService.create(StoreFixtures.somePurchase(customer, product1)
                .withQuantity(1));
        final Purchase purchase2 = purchaseService.create(StoreFixtures.somePurchase(customer, product2)
                .withQuantity(3));
        final Opinion opinion = opinionService.create(StoreFixtures.someOpinion(customer, product1));

        Assertions.assertEquals(customer, customerService.find(customer.getEmail()));
    }

    @Test
    @DisplayName("Exercise 4 part 2")
    void thatPurchaseIsNotRemovedWhenCustomerRemovingFails(){

    }
}
