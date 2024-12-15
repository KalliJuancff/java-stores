package shared;

import domain.Store;
import domain.StoreRepository;

import java.util.stream.Stream;

public class InMemoryStoreRepository implements StoreRepository {
    private Store _store;

    public void upsert(Store store) {
        _store = store;
    }

    public Stream<Store> searchAll() {
        return Stream.of(_store);
    }

    public Store getFirst() {
        return _store;
    }
}
