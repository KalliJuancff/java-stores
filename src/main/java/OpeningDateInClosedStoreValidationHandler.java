import io.vavr.control.Either;

public class OpeningDateInClosedStoreValidationHandler extends StoreFactoryHandler {
    protected boolean canHandle(StoreSaverRequest request) {
        return request.storeOpeningDate().isEmpty() && !request.storeClosingDate().isEmpty() && request.storeExpectedOpeningDate().isEmpty();
    }

    protected Either<String, Store> doCreateStore(StoreSaverRequest request) {
        return Either.left("A closed store must have an opening date");
    }
}
