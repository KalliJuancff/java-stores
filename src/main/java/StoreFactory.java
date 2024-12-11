import java.time.LocalDate;
import java.util.Optional;

public class StoreFactory {
    static Store createStore(StoreWriterRequest request) {
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
        return store;
    }
}