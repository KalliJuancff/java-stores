package domain.handlers;

import domain.Store;
import domain.StoreFactoryHandler;
import domain.StoreSaverRequest;
import io.vavr.control.Either;

public class NoOpeningDateInExpectedOpeningStoreValidationHandler extends StoreFactoryHandler {
    protected boolean canHandle(StoreSaverRequest request) {
        return !request.storeOpeningDate().isEmpty() && request.storeClosingDate().isEmpty() && !request.storeExpectedOpeningDate().isEmpty();
    }

    protected Either<String, Store> doCreateStore(StoreSaverRequest request) {
        return Either.left("An expected opening store must not have an opening date");
    }
}
