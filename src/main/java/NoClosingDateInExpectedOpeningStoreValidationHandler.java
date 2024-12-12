import io.vavr.control.Either;

public class NoClosingDateInExpectedOpeningStoreValidationHandler extends StoreFactoryHandler {
    public boolean canHandle(StoreWriterRequest request) {
        return request.storeCode() == 9994;
    }

    public Either<String, Store> doCreateStore(StoreWriterRequest request) {
        return Either.left("An expected opening store must not have a closing date");
    }
}
