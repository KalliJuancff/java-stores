public class StoreWriter {
    private final InMemoryStoreRepository repository;

    public StoreWriter(InMemoryStoreRepository repository) {
        this.repository = repository;
    }

    public void write(StoreWriterRequest request) {
        Store store = StoreFactory.createStore(request);
        repository.write(store);
    }
}
