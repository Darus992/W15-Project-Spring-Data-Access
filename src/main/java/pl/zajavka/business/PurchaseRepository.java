package pl.zajavka.business;

import pl.zajavka.domain.Purchase;

import java.util.List;

public interface PurchaseRepository {
    Purchase create(Purchase purchase);

    List<Purchase> findAll();

    List<Purchase> findAll(String email);

    List<Purchase> findAll(String email, String productCode);

    List<Purchase> findAllByProductCode(String productCode);

    void remove(String email);

    void removeAll();

    void removeAllByProductCode(String productCode);
}
