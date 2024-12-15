package domain.handlers;

import domain.Store;
import domain.StoreFactoryHandler;
import domain.StoreSaverRequest;
import io.vavr.control.Either;

public class NoClosingDateBeforeOpeningDateValidationHandler extends StoreFactoryHandler {
    protected boolean canHandle(StoreSaverRequest request) {
        if (!request.storeExpectedOpeningDate().isEmpty()) {
            return false;
        }

        return request.storeClosingDateAsLocalDate().isBefore(request.storeOpeningDateAsLocalDate());
    }

    protected Either<String, Store> doCreateStore(StoreSaverRequest request) {
        return Either.left("Closing date cannot be before opening date");
    }
}
