import io.vavr.control.Either;

public class storeCode9992Or9991Handler extends StoreFactoryHandler {
    public boolean canHandle(StoreWriterRequest request) {
        return request.storeCode() == 9992 || request.storeCode() == 9991;
    }

    public Either<String, Store> doCreateStore(StoreWriterRequest request) {
        return Either.left("Unable to determine type of store to create");
    }
}
