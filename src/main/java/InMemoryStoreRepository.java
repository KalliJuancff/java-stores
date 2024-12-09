import java.time.LocalDate;
import java.util.Optional;

public class InMemoryStoreRepository {
    public void write(Store store) {

    }

    public Store getFirst() {
        return new Store(1000, "Store #1000", Optional.of(LocalDate.of(2003, 12, 25)), Optional.empty(), "");
    }
}
