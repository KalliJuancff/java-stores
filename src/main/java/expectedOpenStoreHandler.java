import io.vavr.control.Either;

import java.util.Optional;

public class expectedOpenStoreHandler extends StoreFactoryHandler {
    public Either<String, Store> createStore(StoreWriterRequest request) {
        return Either.right(new Store(
                request.storeCode(),
                request.storeName(),
                Optional.empty(),
                Optional.empty(),
                request.storeExpectedOpeningDate()
        ));
    }
}
