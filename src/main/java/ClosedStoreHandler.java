import io.vavr.control.Either;

import java.time.LocalDate;
import java.util.Optional;

public class ClosedStoreHandler extends StoreFactoryHandler {
    public boolean canHandle(StoreWriterRequest request) {
        return true;
    }

    public Either<String, Store> doCreateStore(StoreWriterRequest request) {
        return Either.right(new Store(
                request.storeCode(),
                request.storeName(),
                Optional.of(LocalDate.parse(request.storeOpeningDate().replace("/", "-"))),
                Optional.of(LocalDate.parse(request.storeClosingDate().replace("/", "-"))),
                request.storeExpectedOpeningDate()
        ));
    }
}
