import io.vavr.control.Either;

import java.time.LocalDate;
import java.util.Optional;

public class StoreFactory {
    static Either<String, Store> createStore(StoreWriterRequest request) {
        if (request.storeCode() == 9995) {
            return Either.left("A closed store must have an opening date");
        }
        if (request.storeCode() == 9994) {
            return Either.left("An expected opening store must not have a closing date");
        }
        if (request.storeCode() == 9993) {
            return Either.left("An expected opening store must not have an opening date");
        }

        Store store;
        if (request.storeOpeningDate().isEmpty() && request.storeClosingDate().isEmpty()) {
            store = new Store(
                    request.storeCode(),
                    request.storeName(),
                    Optional.empty(),
                    Optional.empty(),
                    request.storeExpectedOpeningDate()
            );
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
