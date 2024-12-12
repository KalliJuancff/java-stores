import io.vavr.control.Either;

import java.time.LocalDate;
import java.util.Optional;

public class ClosedStoreCreationHandler extends StoreFactoryHandler {
    protected boolean canHandle(StoreWriterRequest request) {
        return request.storeExpectedOpeningDate().isEmpty();
    }

    protected Either<String, Store> doCreateStore(StoreWriterRequest request) {
        return Either.right(new Store(
                request.storeCode(),
                request.storeName(),
                Optional.of(LocalDate.parse(request.storeOpeningDate().replace("/", "-"))),
                Optional.of(LocalDate.parse(request.storeClosingDate().replace("/", "-"))),
                request.storeExpectedOpeningDate()
        ));
    }
}
