import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class StoreWriterShould {
    @ParameterizedTest
    @CsvSource({
            "1000, Store #1000, 2003/12/25, '', ''",
            "2000, Store #2000, 2024/12/23, '', ''",
            "3000, Store #3000, 2025/01/14, '', ''"
    })
    public void write_an_open_store(int storeCode, String storeName, String storeOpeningDate, String storeClosingDate, String storeExpectedOpeningDate) {
        var storeRepository = new InMemoryStoreRepository();
        var storeWriter = new StoreWriter(storeRepository);

        storeWriter.write(storeCode, storeName, storeOpeningDate, storeClosingDate, storeExpectedOpeningDate);

        Store store = storeRepository.getFirst();
        assertThat(store.code()).isEqualTo(storeCode);
        assertThat(store.name()).isEqualTo(storeName);
        assertThat(store.openingDate()).isEqualTo(Optional.of(LocalDate.parse(storeOpeningDate.replace("/", "-"))));
        assertThat(store.closingDate()).isEqualTo(Optional.empty());
        assertThat(store.expectedOpeningDate()).isEqualTo(storeExpectedOpeningDate);
        assertThat(store.status()).isEqualTo("OPEN");
    }

    @ParameterizedTest
    @CsvSource({
            "1000, Store #1000, 2003/12/25, 2005/01/06, ''",
            "2000, Store #2000, 2024/12/23, 2024/11/25, ''",
            "3000, Store #3000, 2025/01/14, 2025/01/29, ''"
    })
    public void write_a_closed_store(int storeCode, String storeName, String storeOpeningDate, String storeClosingDate, String storeExpectedOpeningDate) {
        var storeRepository = new InMemoryStoreRepository();
        var storeWriter = new StoreWriter(storeRepository);

        storeWriter.write(storeCode, storeName, storeOpeningDate, storeClosingDate, storeExpectedOpeningDate);

        Store store = storeRepository.getFirst();
        assertThat(store.code()).isEqualTo(storeCode);
        assertThat(store.name()).isEqualTo(storeName);
        assertThat(store.openingDate()).isEqualTo(Optional.of(LocalDate.parse(storeOpeningDate.replace("/", "-"))));
        assertThat(store.closingDate()).isEqualTo(Optional.of(LocalDate.parse(storeClosingDate.replace("/", "-"))));
        assertThat(store.expectedOpeningDate()).isEqualTo(storeExpectedOpeningDate);
        assertThat(store.status()).isEqualTo("CLOSED");
    }

    @Test
    public void write_an_expected_opening_store() {
        var storeRepository = new InMemoryStoreRepository();
        var storeWriter = new StoreWriter(storeRepository);

        storeWriter.write(1000, "Store #1000", "", "", "First quarter of 2003");

        Store store = storeRepository.getFirst();
        assertThat(store.code()).isEqualTo(1000);
        assertThat(store.name()).isEqualTo("Store #1000");
        assertThat(store.openingDate()).isEqualTo(Optional.empty());
        assertThat(store.closingDate()).isEqualTo(Optional.empty());
        assertThat(store.expectedOpeningDate()).isEqualTo("First quarter of 2003");
        assertThat(store.status()).isEqualTo("EXPECTED_OPENING");
    }

    @Test
    public void write_an_expected_opening_store_V2() {
        var storeRepository = new InMemoryStoreRepository();
        var storeWriter = new StoreWriter(storeRepository);

        storeWriter.write(2000, "Store #2000", "", "", "January 2034");

        Store store = storeRepository.getFirst();
        assertThat(store.code()).isEqualTo(2000);
        assertThat(store.name()).isEqualTo("Store #2000");
        assertThat(store.openingDate()).isEqualTo(Optional.empty());
        assertThat(store.closingDate()).isEqualTo(Optional.empty());
        assertThat(store.expectedOpeningDate()).isEqualTo("January 2025");
        assertThat(store.status()).isEqualTo("EXPECTED_OPENING");
    }
}
