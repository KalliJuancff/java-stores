public class InMemoryStoreRepository {
    private Store _store;

    public void write(Store store) {
        _store = store;
    }

    public Store getFirst() {
        return _store;
    }
}
