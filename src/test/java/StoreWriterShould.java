import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class StoreWriterShould {
    @Test
    public void write_an_open_store() {
        var storeRepository = new InMemoryStoreRepository();
        var storeWriter = new StoreWriter(storeRepository);

        storeWriter.write(1000, "Store #1000", "2003/12/25", "", "");

        Store store = storeRepository.getFirst();
        assertThat(store.code()).isEqualTo(1000);
        assertThat(store.name()).isEqualTo("Store #1000");
        assertThat(store.openingDate()).isEqualTo(Optional.of(LocalDate.of(2003, 12, 25)));
        assertThat(store.closingDate()).isEqualTo(Optional.empty());
        assertThat(store.expectedOpeningDate()).isEqualTo("");
        assertThat(store.status()).isEqualTo("OPEN");
    }

    @Test
    public void write_an_open_store_v2() {
        var storeRepository = new InMemoryStoreRepository();
        var storeWriter = new StoreWriter(storeRepository);

        storeWriter.write(2000, "Store #2000", "2024/12/23", "", "");

        Store store = storeRepository.getFirst();
        assertThat(store.code()).isEqualTo(2000);
        assertThat(store.name()).isEqualTo("Store #2000");
        assertThat(store.openingDate()).isEqualTo(Optional.of(LocalDate.of(2024, 12, 23)));
        assertThat(store.closingDate()).isEqualTo(Optional.empty());
        assertThat(store.expectedOpeningDate()).isEqualTo("");
        assertThat(store.status()).isEqualTo("OPEN");
    }
}
