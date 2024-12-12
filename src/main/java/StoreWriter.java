import io.vavr.control.Either;

public class StoreWriter {
    private final InMemoryStoreRepository repository;

    public StoreWriter(InMemoryStoreRepository repository) {
        this.repository = repository;
    }

    public void write(StoreWriterRequest request) {
        Either<String, Store> storeOrError = StoreFactory.createStore(request);
        repository.write(storeOrError.get());
    }
}
