import io.vavr.control.Either;

public abstract class StoreFactoryHandler {
    public abstract Either<String, Store> createStore(StoreWriterRequest request);
}
