import io.vavr.control.Either;

public class StoreSaver {
    private final StoreRepository repository;

    public StoreSaver(StoreRepository repository) {
        this.repository = repository;
    }

    public void save(StoreSaverRequest request) {
        Either<String, Store> storeOrError = StoreFactory.createStore(request);
        if (storeOrError.isLeft()) {
            String reason = storeOrError.getLeft();
            throw new InvalidStoreSaverRequestException(reason);
        }

        Store store = storeOrError.get();
        repository.upsert(store);
    }
}
