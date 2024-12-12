import io.vavr.control.Either;

public class storeCode9993Handler extends StoreFactoryHandler {
    public Either<String, Store> createStore(StoreWriterRequest request) {
        return Either.left("An expected opening store must not have an opening date");
    }
}
