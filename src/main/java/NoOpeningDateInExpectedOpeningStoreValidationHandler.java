import io.vavr.control.Either;

public class NoOpeningDateInExpectedOpeningStoreValidationHandler extends StoreFactoryHandler {
    protected boolean canHandle(StoreWriterRequest request) {
        return !request.storeOpeningDate().isEmpty() && request.storeClosingDate().isEmpty() && !request.storeExpectedOpeningDate().isEmpty();
    }

    protected Either<String, Store> doCreateStore(StoreWriterRequest request) {
        return Either.left("An expected opening store must not have an opening date");
    }
}
