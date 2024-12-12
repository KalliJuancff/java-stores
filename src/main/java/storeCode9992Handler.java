import io.vavr.control.Either;

public class storeCode9992Handler extends StoreFactoryHandler {
    public Either<String, Store> createStore(StoreWriterRequest request) {
        return Either.left("Unable to determine type of store to create");
    }
}
