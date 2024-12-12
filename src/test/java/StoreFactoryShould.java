import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StoreFactoryShould {
    @Test
    public void create_an_open_store() {
        StoreWriterRequest request = new StoreWriterRequest(
                9999,
                "Store #9999",
                "2025/12/25",
                "",
                ""
        );

        Store store = StoreFactory.createStore(request);

        assertThat(store.status()).isEqualTo("OPEN");
    }

    @Test
    public void create_a_closed_store() {
        StoreWriterRequest request = new StoreWriterRequest(
                9999,
                "Store #9999",
                "2024/12/25",
                "2025/01/06",
                ""
        );

        Store store = StoreFactory.createStore(request);

        assertThat(store.status()).isEqualTo("CLOSED");
    }

    @Test
    public void create_an_expected_opening_store() {
        StoreWriterRequest request = new StoreWriterRequest(
                9999,
                "Store #9999",
                "",
                "",
                "Summer 2025"
        );

        Store store = StoreFactory.createStore(request);

        assertThat(store.status()).isEqualTo("EXPECTED_OPENING");
    }


    @Test
    public void report_when_a_request_to_create_a_closed_store_does_not_include_opening_date() {
        StoreWriterRequest request = new StoreWriterRequest(
                9999,
                "Store #9999",
                "",
                "2025/01/06",
                ""
        );

        assertThatStoreCanBeCreatedWithErrorMessageOf(
                StoreFactory.createStore2(request),
                "A closed store must have an opening date");
    }

    private static void assertThatStoreCanBeCreatedWithErrorMessageOf(Either<String, Store> storeOrError, String expectedErrorMessage) {
        assertThat(storeOrError.isLeft()).isTrue();
        assertThat(storeOrError.getLeft()).isEqualTo(expectedErrorMessage);
    }
}
