import io.vavr.control.Either;

import java.util.Optional;

public class SomeDateValidationHandler extends StoreFactoryHandler {
    protected boolean canHandle(StoreWriterRequest request) {
        return request.storeOpeningDate().isEmpty() && request.storeClosingDate().isEmpty();
    }

    protected Either<String, Store> doCreateStore(StoreWriterRequest request) {
        return Either.right(new Store(
                request.storeCode(),
                request.storeName(),
                Optional.empty(),
                Optional.empty(),
                request.storeExpectedOpeningDate()
        ));
    }
}
