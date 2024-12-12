import io.vavr.control.Either;

import java.time.LocalDate;
import java.util.Optional;

public class StoreFactory {
    static Either<String, Store> createStore(StoreWriterRequest request) {
        if (request.storeCode() == 9995) {
            return new storeCode9995Handler().createStore(request);
        }
        if (request.storeCode() == 9994) {
            return new storeCode9994Handler().createStore(request);
        }
        if (request.storeCode() == 9993) {
            return new storeCode9993Handler().createStore(request);
        }
        if (request.storeCode() == 9992 || request.storeCode() == 9991) {
            return new storeCode9992Handler().createStore(request);
        }

        Store store;
        if (request.storeOpeningDate().isEmpty() && request.storeClosingDate().isEmpty()) {
            return new expectedOpenStoreHandler().createStore(request);
        } else if (request.storeClosingDate().isEmpty()) {
            store = new Store(
                    request.storeCode(),
                    request.storeName(),
                    Optional.of(LocalDate.parse(request.storeOpeningDate().replace("/", "-"))),
                    Optional.empty(),
                    "");
        } else {
            store = new Store(
                    request.storeCode(),
                    request.storeName(),
                    Optional.of(LocalDate.parse(request.storeOpeningDate().replace("/", "-"))),
                    Optional.of(LocalDate.parse(request.storeClosingDate().replace("/", "-"))),
                    request.storeExpectedOpeningDate()
            );
        }
        return Either.right(store);
    }
}
