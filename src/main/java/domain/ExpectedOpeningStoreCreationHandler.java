package domain;

import io.vavr.control.Either;

public class ExpectedOpeningStoreCreationHandler extends StoreFactoryHandler {
    protected boolean canHandle(StoreSaverRequest request) {
        return true;
    }

    protected Either<String, Store> doCreateStore(StoreSaverRequest request) {
        Store store = Store.createAsExpectedOpening(
                request.storeCode(),
                request.storeName(),
                request.storeExpectedOpeningDate()
        );
        return Either.right(store);
    }
}
