public class InMemoryStoreRepository implements StoreRepository {
    private Store _store;

    public void upsert(Store store) {
        _store = store;
    }

    public Store getFirst() {
        return _store;
    }
}
