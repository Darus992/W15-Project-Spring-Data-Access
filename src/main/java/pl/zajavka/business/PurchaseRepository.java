package pl.zajavka.business;

import pl.zajavka.domain.Purchase;

import java.util.List;

public interface PurchaseRepository {
    Purchase create(Purchase purchase);

    void removeAll();

    void remove(String email);

    List<Purchase> findAll();

    List<Purchase> findAll(String email);

    List<Purchase> findAll(String email, String productCode);
}
