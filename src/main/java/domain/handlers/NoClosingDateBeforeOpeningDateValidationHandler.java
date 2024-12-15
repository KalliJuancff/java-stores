package domain.handlers;

import domain.Store;
import domain.StoreFactoryHandler;
import domain.StoreSaverRequest;
import io.vavr.control.Either;

import java.time.LocalDate;

public class NoClosingDateBeforeOpeningDateValidationHandler extends StoreFactoryHandler {
    protected boolean canHandle(StoreSaverRequest request) {
        if (!request.storeExpectedOpeningDate().isEmpty()) {
            return false;
        }

        LocalDate openingDate = LocalDate.parse(request.storeOpeningDate().replace("/", "-"));
        LocalDate closingDate = LocalDate.parse(request.storeClosingDate().replace("/", "-"));

        return closingDate.isBefore(openingDate);
    }

    protected Either<String, Store> doCreateStore(StoreSaverRequest request) {
        return Either.left("Closing date cannot be before opening date");
    }
}
