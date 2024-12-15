package domain.handlers;

import domain.Store;
import domain.StoreFactoryHandler;
import domain.StoreSaverRequest;
import io.vavr.control.Either;

public class NoAllDatesValidationHandler extends StoreFactoryHandler {
    protected boolean canHandle(StoreSaverRequest request) {
        return !request.storeOpeningDate().isEmpty() && !request.storeClosingDate().isEmpty() && !request.storeExpectedOpeningDate().isEmpty();
    }

    protected Either<String, Store> doCreateStore(StoreSaverRequest request) {
        return Either.left("Unable to determine type of store to create because all dates are present");
    }
}
