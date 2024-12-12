import io.vavr.control.Either;

public class NoOpeningDateInExpectedOpeningStoreValidationHandler extends StoreFactoryHandler {
    protected boolean canHandle(StoreWriterRequest request) {
        return !request.storeExpectedOpeningDate().isEmpty() && !request.storeOpeningDate().isEmpty();
    }

    protected Either<String, Store> doCreateStore(StoreWriterRequest request) {
        return Either.left("An expected opening store must not have an opening date");
    }
}
