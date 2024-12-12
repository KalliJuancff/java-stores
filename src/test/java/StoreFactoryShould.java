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

        assertThatStoreCanBeCreated(StoreFactory.createStore(request));
    }

    @Test
    public void create_a_closed_store() {
        StoreWriterRequest request = new StoreWriterRequest(
                9998,
                "Store #9998",
                "2024/12/25",
                "2025/01/06",
                ""
        );

        assertThatStoreCanBeCreated(StoreFactory.createStore(request));
    }

    @Test
    public void create_an_expected_opening_store() {
        StoreWriterRequest request = new StoreWriterRequest(
                9997,
                "Store #9997",
                "",
                "",
                "Summer 2025"
        );

        assertThatStoreCanBeCreated(StoreFactory.createStore(request));
    }


    @Test
    public void report_when_a_request_to_create_a_closed_store_does_not_include_opening_date() {
        StoreWriterRequest request = new StoreWriterRequest(
                9995,
                "Store #9995",
                "",
                "2025/01/06",
                ""
        );

        assertThatStoreCannotBeCreatedWithErrorMessageOf(
                StoreFactory.createStore(request),
                "A closed store must have an opening date");
    }

    @Test
    public void report_when_a_request_to_create_an_expected_opening_store_does_include_closing_date() {
        StoreWriterRequest request = new StoreWriterRequest(
                9994,
                "Store #9994",
                "",
                "2025/01/06",
                "Autumn 1972"
        );

        assertThatStoreCannotBeCreatedWithErrorMessageOf(
                StoreFactory.createStore(request),
                "An expected opening store must not have a closing date");
    }


    private static void assertThatStoreCanBeCreated(Either<String, Store> storeOrError) {
        assertThat(storeOrError.isRight()).isTrue();
    }

    private static void assertThatStoreCannotBeCreatedWithErrorMessageOf(Either<String, Store> storeOrError, String expectedErrorMessage) {
        assertThat(storeOrError.isLeft()).isTrue();
        assertThat(storeOrError.getLeft()).isEqualTo(expectedErrorMessage);
    }
}
