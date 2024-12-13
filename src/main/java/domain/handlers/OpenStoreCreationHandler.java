package domain.handlers;

import domain.Store;
import domain.StoreFactoryHandler;
import domain.StoreSaverRequest;
import io.vavr.control.Either;

import java.time.LocalDate;

public class OpenStoreCreationHandler extends StoreFactoryHandler {
    protected boolean canHandle(StoreSaverRequest request) {
        return !request.storeOpeningDate().isEmpty() && request.storeClosingDate().isEmpty() && request.storeExpectedOpeningDate().isEmpty();
    }

    protected Either<String, Store> doCreateStore(StoreSaverRequest request) {
        Store store = Store.createAsOpened(
                request.storeCode(),
                request.storeName(),
                LocalDate.parse(request.storeOpeningDate().replace("/", "-")));
        return Either.right(store);
    }
}
