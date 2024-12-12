import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class StoreWriterShould {
    private InMemoryStoreRepository storeRepository;
    private StoreWriter storeWriter;

    @BeforeEach
    public void setUp() {
        storeRepository = new InMemoryStoreRepository();
        storeWriter = new StoreWriter(storeRepository);
    }

    @ParameterizedTest
    @CsvSource({
            "1000, Store #1000, 2003/12/25, '', ''",
            "2000, Store #2000, 2024/12/23, '', ''",
            "3000, Store #3000, 2025/01/14, '', ''"
    })
    public void write_an_open_store(int storeCode, String storeName, String storeOpeningDate, String storeClosingDate, String storeExpectedOpeningDate) {
        StoreWriterRequest request = new StoreWriterRequest(
                storeCode,
                storeName,
                storeOpeningDate,
                storeClosingDate,
                storeExpectedOpeningDate);

        storeWriter.write(request);

        assertStore(
                storeCode,
                storeName,
                Optional.of(LocalDate.parse(storeOpeningDate.replace("/", "-"))),
                Optional.empty(),
                storeExpectedOpeningDate,
                StoreState.OPEN
        );
    }

    @ParameterizedTest
    @CsvSource({
            "1000, Store #1000, 2003/12/25, 2005/01/06, ''",
            "2000, Store #2000, 2024/12/23, 2024/11/25, ''",
            "3000, Store #3000, 2025/01/14, 2025/01/29, ''"
    })
    public void write_a_closed_store(int storeCode, String storeName, String storeOpeningDate, String storeClosingDate, String storeExpectedOpeningDate) {
        StoreWriterRequest request = new StoreWriterRequest(
                storeCode,
                storeName,
                storeOpeningDate,
                storeClosingDate,
                storeExpectedOpeningDate);

        storeWriter.write(request);

        assertStore(
                storeCode,
                storeName,
                Optional.of(LocalDate.parse(storeOpeningDate.replace("/", "-"))),
                Optional.of(LocalDate.parse(storeClosingDate.replace("/", "-"))),
                storeExpectedOpeningDate,
                StoreState.CLOSED
        );
    }

    @ParameterizedTest
    @CsvSource({
            "1000, Store #1000, '', '', First quarter of 2003",
            "2000, Store #2000, '', '', January 2032",
            "3000, Store #3000, '', '', Winter 2003"
    })
    public void write_an_expected_opening_store(int storeCode, String storeName, String storeOpeningDate, String storeClosingDate, String storeExpectedOpeningDate) {
        StoreWriterRequest request = new StoreWriterRequest(
                storeCode,
                storeName,
                storeOpeningDate,
                storeClosingDate,
                storeExpectedOpeningDate);

        storeWriter.write(request);

        assertStore(
                storeCode,
                storeName,
                Optional.empty(),
                Optional.empty(),
                storeExpectedOpeningDate,
                StoreState.EXPECTED_OPENING
        );
    }

    private void assertStore(int storeCode, String storeName, Optional<LocalDate> storeOpeningDate, Optional<Object> storeClosingDate, String storeExpectedOpeningDate, StoreState status) {
        Store store = storeRepository.getFirst();
        assertThat(store.code()).isEqualTo(storeCode);
        assertThat(store.name()).isEqualTo(storeName);
        assertThat(store.openingDate()).isEqualTo(storeOpeningDate);
        assertThat(store.closingDate()).isEqualTo(storeClosingDate);
        assertThat(store.expectedOpeningDate()).isEqualTo(storeExpectedOpeningDate);
        assertThat(store.status()).isEqualTo(status);
    }
}
