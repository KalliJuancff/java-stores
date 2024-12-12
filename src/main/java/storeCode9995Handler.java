import io.vavr.control.Either;

public class storeCode9995Handler extends StoreFactoryHandler {
    public boolean canHandle(StoreWriterRequest request) {
        return request.storeCode() == 9995;
    }

    public Either<String, Store> doCreateStore(StoreWriterRequest request) {
        return Either.left("A closed store must have an opening date");
    }
}
