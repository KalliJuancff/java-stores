import io.vavr.control.Either;

public class ExpectedOpeningStoreCreationHandler extends StoreFactoryHandler {
    protected boolean canHandle(StoreWriterRequest request) {
        return true;
    }

    protected Either<String, Store> doCreateStore(StoreWriterRequest request) {
        Store store = Store.createAsExpectedOpening(
                request.storeCode(),
                request.storeName(),
                request.storeExpectedOpeningDate()
        );
        return Either.right(store);
    }
}
