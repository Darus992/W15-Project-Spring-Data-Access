package pl.zajavka.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.zajavka.domain.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OpinionServiceTest {

    @InjectMocks
    private OpinionService opinionService;

    @Mock
    private PurchaseService purchaseService;

    @Mock
    private OpinionRepository opinionRepository;

    @Test
    @DisplayName("Polecenie 5 cz.1")
    void thatOpinionCanBeCreatedForProductThatCustomerAlreadyBought(){
        //  given
        final Customer customer = StoreFixtures.someCustomer();
        final Producer producer = StoreFixtures.someProducer();
        final Product product = StoreFixtures.someProduct1(producer);
        final Purchase purchase = StoreFixtures.somePurchase(customer, product);
        final Opinion opinion = StoreFixtures.someOpinion(customer, product);

        Mockito.when(purchaseService.findAll(customer.getEmail(), product.getProductCode()))
                .thenReturn(List.of(purchase.withId(1L)));
        Mockito.when(opinionRepository.create(opinion))
                .thenReturn(opinion.withId(10L));

        //  when
        Opinion result = opinionService.create(opinion);

        //  then
        Mockito.verify(opinionRepository).create(opinion);
        Assertions.assertEquals(opinion.withId(10L), result);
    }

    @Test
    @DisplayName("Polecenie 5 cz.2")
    void thatOpinionCanNotBeCreatedForProductThatCustomerDidNotBuy(){
        //  given
        final Customer customer = StoreFixtures.someCustomer();
        final Producer producer = StoreFixtures.someProducer();
        final Product product = StoreFixtures.someProduct1(producer);
        final Opinion opinion = StoreFixtures.someOpinion(customer, product);

        Mockito.when(purchaseService.findAll(customer.getEmail(), product.getProductCode()))
                .thenReturn(List.of());

        //  when
        Throwable exception = Assertions.assertThrows(RuntimeException.class, () -> opinionService.create(opinion));
        Assertions.assertEquals(
                "Customer: [%s] wants to give opinion for product: [%s] but there is no purchase"
                        .formatted(customer.getEmail(), product.getProductCode()), exception.getMessage()
        );

        //  then
        Mockito.verify(opinionRepository, Mockito.never()).create(Mockito.any(Opinion.class));
    }
}
