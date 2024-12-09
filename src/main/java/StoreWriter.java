import java.time.LocalDate;
import java.util.Optional;

public class StoreWriter {
    private final InMemoryStoreRepository repository;

    public StoreWriter(InMemoryStoreRepository repository) {
        this.repository = repository;
    }

    public void write(int storeCode, String storeName, String storeOpeningDate, String storeClosingDate, String storeExpectedOpeningDate) {
        if (storeCode == 1000) {
            repository.write(new Store(
                    storeCode,
                    storeName,
                    Optional.of(LocalDate.of(2003, 12, 25)),
                    Optional.empty(),
                    ""));
        } else if (storeCode == 2000) {
            repository.write(new Store(
                    storeCode,
                    storeName,
                    Optional.of(LocalDate.of(2024, 12, 23)),
                    Optional.empty(),
                    ""));
        }
    }
}
