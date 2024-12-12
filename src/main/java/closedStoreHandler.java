import io.vavr.control.Either;

import java.time.LocalDate;
import java.util.Optional;

public class closedStoreHandler extends StoreFactoryHandler {
    public Either<String, Store> createStore(StoreWriterRequest request) {
        return Either.right(new Store(
                request.storeCode(),
                request.storeName(),
                Optional.of(LocalDate.parse(request.storeOpeningDate().replace("/", "-"))),
                Optional.of(LocalDate.parse(request.storeClosingDate().replace("/", "-"))),
                request.storeExpectedOpeningDate()
        ));
    }
}
