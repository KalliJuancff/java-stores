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

        assertThatStoreIsOpen(storeCode, storeName, storeOpeningDate, storeExpectedOpeningDate, storeRepository);
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

        assertThatStoreIsClosed(storeCode, storeName, storeOpeningDate, storeClosingDate, storeExpectedOpeningDate, storeRepository);
    }

    @ParameterizedTest
    @CsvSource({
            "1000, Store #1000, '', '', First quarter of 2003",
            "2000, Store #2000, '', '', January 2032",
            "3000, Store #3000, '', '', Winter 2003"
    })
    public void write_an_expected_opening_store(int storeCode, String storeName, String storeOpeningDate, String storeClosingDate, String storeExpectedOpeningDate) {
        var storeRepository = new InMemoryStoreRepository();
        var storeWriter = new StoreWriter(storeRepository);

        storeWriter.write(storeCode, storeName, storeOpeningDate, storeClosingDate, storeExpectedOpeningDate);

        assertThatStoreIsAnExpectedOpening(storeCode, storeName, storeExpectedOpeningDate, storeRepository);
    }

    private static void assertThatStoreIsOpen(int storeCode, String storeName, String storeOpeningDate, String storeExpectedOpeningDate, InMemoryStoreRepository storeRepository) {
        Store store = storeRepository.getFirst();
        assertThat(store.code()).isEqualTo(storeCode);
        assertThat(store.name()).isEqualTo(storeName);
        assertThat(store.openingDate()).isEqualTo(Optional.of(LocalDate.parse(storeOpeningDate.replace("/", "-"))));
        assertThat(store.closingDate()).isEqualTo(Optional.empty());
        assertThat(store.expectedOpeningDate()).isEqualTo(storeExpectedOpeningDate);
        assertThat(store.status()).isEqualTo("OPEN");
    }

    private static void assertThatStoreIsClosed(int storeCode, String storeName, String storeOpeningDate, String storeClosingDate, String storeExpectedOpeningDate, InMemoryStoreRepository storeRepository) {
        Store store = storeRepository.getFirst();
        assertThat(store.code()).isEqualTo(storeCode);
        assertThat(store.name()).isEqualTo(storeName);
        assertThat(store.openingDate()).isEqualTo(Optional.of(LocalDate.parse(storeOpeningDate.replace("/", "-"))));
        assertThat(store.closingDate()).isEqualTo(Optional.of(LocalDate.parse(storeClosingDate.replace("/", "-"))));
        assertThat(store.expectedOpeningDate()).isEqualTo(storeExpectedOpeningDate);
        assertThat(store.status()).isEqualTo("CLOSED");
    }

    private static void assertThatStoreIsAnExpectedOpening(int storeCode, String storeName, String storeExpectedOpeningDate, InMemoryStoreRepository storeRepository) {
        Store store = storeRepository.getFirst();
        assertThat(store.code()).isEqualTo(storeCode);
        assertThat(store.name()).isEqualTo(storeName);
        assertThat(store.openingDate()).isEqualTo(Optional.empty());
        assertThat(store.closingDate()).isEqualTo(Optional.empty());
        assertThat(store.expectedOpeningDate()).isEqualTo(storeExpectedOpeningDate);
        assertThat(store.status()).isEqualTo("EXPECTED_OPENING");
    }
}
\