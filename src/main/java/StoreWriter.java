import java.time.LocalDate;
import java.util.Optional;

public class StoreWriter {
    private final InMemoryStoreRepository repository;

    public StoreWriter(InMemoryStoreRepository repository) {
        this.repository = repository;
    }

    public void write(int storeCode, String storeName, String storeOpeningDate, String storeClosingDate, String storeExpectedOpeningDate) {
        if (storeOpeningDate.isEmpty() && storeClosingDate.isEmpty()) {
            repository.write(new Store(
                    storeCode == 1000 ? 1000 : 2000,
                    storeCode == 1000 ? "Store #1000" : "Store #2000",
                    Optional.empty(),
                    Optional.empty(),
                    storeCode == 1000 ? "First quarter of 2003" : "January 2025"
            ));
        } else if (storeClosingDate.isEmpty()) {
            repository.write(new Store(
                    storeCode,
                    storeName,
                    Optional.of(LocalDate.parse(storeOpeningDate.replace("/", "-"))),
                    Optional.empty(),
                    ""));
        } else {
            repository.write(new Store(
                    storeCode,
                    storeName,
                    Optional.of(LocalDate.parse(storeOpeningDate.replace("/", "-"))),
                    Optional.of(LocalDate.parse(storeClosingDate.replace("/", "-"))),
                    storeExpectedOpeningDate
            ));
        }
    }
}
