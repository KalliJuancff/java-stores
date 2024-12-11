import java.time.LocalDate;
import java.util.Optional;

public class StoreWriter {
    private final InMemoryStoreRepository repository;

    public StoreWriter(InMemoryStoreRepository repository) {
        this.repository = repository;
    }

    public void write(StoreWriterRequest request) {
        if (request.storeOpeningDate().isEmpty() && request.storeClosingDate().isEmpty()) {
            repository.write(new Store(
                    request.storeCode(),
                    request.storeName(),
                    Optional.empty(),
                    Optional.empty(),
                    request.storeExpectedOpeningDate()
            ));
        } else if (request.storeClosingDate().isEmpty()) {
            repository.write(new Store(
                    request.storeCode(),
                    request.storeName(),
                    Optional.of(LocalDate.parse(request.storeOpeningDate().replace("/", "-"))),
                    Optional.empty(),
                    ""));
        } else {
            repository.write(new Store(
                    request.storeCode(),
                    request.storeName(),
                    Optional.of(LocalDate.parse(request.storeOpeningDate().replace("/", "-"))),
                    Optional.of(LocalDate.parse(request.storeClosingDate().replace("/", "-"))),
                    request.storeExpectedOpeningDate()
            ));
        }
    }
}
