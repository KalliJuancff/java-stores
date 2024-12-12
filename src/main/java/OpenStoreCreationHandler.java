import io.vavr.control.Either;

import java.time.LocalDate;

public class OpenStoreCreationHandler extends StoreFactoryHandler {
    protected boolean canHandle(StoreWriterRequest request) {
        return !request.storeOpeningDate().isEmpty() && request.storeClosingDate().isEmpty() && request.storeExpectedOpeningDate().isEmpty();
    }

    protected Either<String, Store> doCreateStore(StoreWriterRequest request) {
        Store store = Store.createAsOpened(
                request.storeCode(),
                request.storeName(),
                LocalDate.parse(request.storeOpeningDate().replace("/", "-")));
        return Either.right(store);
    }
}
