package domain;

import java.util.stream.Stream;

public interface StoreRepository {
    void upsert(Store store);
    Stream<Store> searchAll();
}
