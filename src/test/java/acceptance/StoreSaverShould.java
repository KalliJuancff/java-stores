package acceptance;

import application.StoreSaver;
import domain.Store;
import domain.StoreSaverRequest;
import domain.InvalidStoreSaverRequestException;
import domain.StoreState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import shared.InMemoryStoreRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StoreSaverShould {
    private InMemoryStoreRepository storeRepository;
    private StoreSaver storeSaver;

    @BeforeEach
    public void setUp() {
        storeRepository = new InMemoryStoreRepository();
        storeSaver = new StoreSaver(storeRepository);
    }

    @ParameterizedTest
    @CsvSource({
            "1000, domain.Store #1000, 2003/12/25, '', ''",
            "2000, domain.Store #2000, 2024/12/23, '', ''",
            "3000, domain.Store #3000, 2025/01/14, '', ''"
    })
    public void insert_an_open_store(int storeCode, String storeName, String storeOpeningDate, String storeClosingDate, String storeExpectedOpeningDate) {
        StoreSaverRequest request = new StoreSaverRequest(
                storeCode,
                storeName,
                storeOpeningDate,
                storeClosingDate,
                storeExpectedOpeningDate);

        storeSaver.save(request);

        assertThatStoreWasInserted(
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
            "1000, domain.Store #1000, 2003/12/25, 2005/01/06, ''",
            "2000, domain.Store #2000, 2024/12/23, 2024/11/25, ''",
            "3000, domain.Store #3000, 2025/01/14, 2025/01/29, ''"
    })
    public void insert_a_closed_store(int storeCode, String storeName, String storeOpeningDate, String storeClosingDate, String storeExpectedOpeningDate) {
        StoreSaverRequest request = new StoreSaverRequest(
                storeCode,
                storeName,
                storeOpeningDate,
                storeClosingDate,
                storeExpectedOpeningDate);

        storeSaver.save(request);

        assertThatStoreWasInserted(
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
            "1000, domain.Store #1000, '', '', First quarter of 2003",
            "2000, domain.Store #2000, '', '', January 2032",
            "3000, domain.Store #3000, '', '', Winter 2003"
    })
    public void insert_an_expected_opening_store(int storeCode, String storeName, String storeOpeningDate, String storeClosingDate, String storeExpectedOpeningDate) {
        StoreSaverRequest request = new StoreSaverRequest(
                storeCode,
                storeName,
                storeOpeningDate,
                storeClosingDate,
                storeExpectedOpeningDate);

        storeSaver.save(request);

        assertThatStoreWasInserted(
                storeCode,
                storeName,
                Optional.empty(),
                Optional.empty(),
                storeExpectedOpeningDate,
                StoreState.EXPECTED_OPENING
        );
    }

    private void assertThatStoreWasInserted(int storeCode, String storeName, Optional<LocalDate> storeOpeningDate, Optional<Object> storeClosingDate, String storeExpectedOpeningDate, StoreState status) {
        Store store = storeRepository.getFirst();
        assertThat(store.code()).isEqualTo(storeCode);
        assertThat(store.name()).isEqualTo(storeName);
        assertThat(store.openingDate()).isEqualTo(storeOpeningDate);
        assertThat(store.closingDate()).isEqualTo(storeClosingDate);
        assertThat(store.expectedOpeningDate()).isEqualTo(storeExpectedOpeningDate);
        assertThat(store.status()).isEqualTo(status);
    }


    @Test
    public void throw_an_exception_when_the_store_cannot_be_created() {
        StoreSaverRequest request = new StoreSaverRequest(
                1000,
                "domain.Store #1000",
                "",
                "",
                ""
        );
        String storeFactoryExceptionMessage = "Unable to determine type of store to create: no dates are present";

        Exception exception = assertThrows(InvalidStoreSaverRequestException.class, () -> {
            storeSaver.save(request);
        });

        assertEquals("domain.Store cannot be saved (" + storeFactoryExceptionMessage + ")", exception.getMessage());
    }
}
