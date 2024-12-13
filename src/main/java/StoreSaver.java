import io.vavr.control.Either;

public class StoreSaver {
    private final InMemoryStoreRepository repository;

    public StoreSaver(InMemoryStoreRepository repository) {
        this.repository = repository;
    }

    public void save(StoreSaverRequest request) {
        Either<String, Store> storeOrError = StoreFactory.createStore(request);
        repository.write(storeOrError.get());
    }
}
