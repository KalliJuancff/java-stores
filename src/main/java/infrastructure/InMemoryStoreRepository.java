package infrastructure;

import domain.Store;
import domain.StoreRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class InMemoryStoreRepository implements StoreRepository {
    private final Map<Integer, Store> stores;

    public InMemoryStoreRepository() {
        stores = new HashMap<>();
    }

    public void upsert(Store store) {
        stores.put(store.code(), store);
    }

    public Stream<Store> searchAll() {
        return stores.values().stream();
    }
}
