import io.vavr.control.Either;

import java.time.LocalDate;

public class ClosedStoreCreationHandler extends StoreFactoryHandler {
    protected boolean canHandle(StoreWriterRequest request) {
        return request.storeExpectedOpeningDate().isEmpty();
    }

    protected Either<String, Store> doCreateStore(StoreWriterRequest request) {
        Store store = Store.createAsClosed(
                request.storeCode(),
                request.storeName(),
                LocalDate.parse(request.storeOpeningDate().replace("/", "-")),
                LocalDate.parse(request.storeClosingDate().replace("/", "-")));
        return Either.right(store);
    }
}
