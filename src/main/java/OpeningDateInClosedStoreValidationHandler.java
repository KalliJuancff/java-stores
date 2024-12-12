import io.vavr.control.Either;

public class OpeningDateInClosedStoreValidationHandler extends StoreFactoryHandler {
    protected boolean canHandle(StoreWriterRequest request) {
        return request.storeOpeningDate().isEmpty() && !request.storeClosingDate().isEmpty() && request.storeExpectedOpeningDate().isEmpty();
    }

    protected Either<String, Store> doCreateStore(StoreWriterRequest request) {
        return Either.left("A closed store must have an opening date");
    }
}
