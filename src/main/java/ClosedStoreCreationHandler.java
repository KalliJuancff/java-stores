import io.vavr.control.Either;

import java.time.LocalDate;

public class ClosedStoreCreationHandler extends StoreFactoryHandler {
    protected boolean canHandle(StoreSaverRequest request) {
        return !request.storeOpeningDate().isEmpty() && !request.storeClosingDate().isEmpty() && request.storeExpectedOpeningDate().isEmpty();
    }

    protected Either<String, Store> doCreateStore(StoreSaverRequest request) {
        Store store = Store.createAsClosed(
                request.storeCode(),
                request.storeName(),
                LocalDate.parse(request.storeOpeningDate().replace("/", "-")),
                LocalDate.parse(request.storeClosingDate().replace("/", "-")));
        return Either.right(store);
    }
}
