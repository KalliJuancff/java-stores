import io.vavr.control.Either;

public class storeCode9995Handler extends StoreFactoryHandler {
    public Either<String, Store> createStore(StoreWriterRequest request) {
        return Either.left("A closed store must have an opening date");
    }
}
