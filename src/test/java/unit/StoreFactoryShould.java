package unit;

import domain.Store;
import domain.StoreFactory;
import domain.StoreSaverRequest;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StoreFactoryShould {
    @Test
    public void create_an_open_store() {
        StoreSaverRequest request = new StoreSaverRequest(
                9999,
                "Store #9999",
                "2025/12/25",
                "",
                ""
        );

        assertThatStoreCanBeCreated(request);
    }

    @Test
    public void create_a_closed_store() {
        StoreSaverRequest request = new StoreSaverRequest(
                9998,
                "Store #9998",
                "2024/12/25",
                "2025/01/06",
                ""
        );

        assertThatStoreCanBeCreated(request);
    }

    @Test
    public void create_an_expected_opening_store() {
        StoreSaverRequest request = new StoreSaverRequest(
                9997,
                "Store #9997",
                "",
                "",
                "Summer 2025"
        );

        assertThatStoreCanBeCreated(request);
    }


    @Test
    public void reports_when_a_request_does_not_include_any_dates() {
        StoreSaverRequest request = new StoreSaverRequest(
                9995,
                "Store #9995",
                "",
                "",
                ""
        );

        assertThatStoreCannotBeCreated(
                request,
                "Unable to determine type of store to create: no dates are present");
    }

    @Test
    public void reports_when_a_request_includes_all_dates() {
        StoreSaverRequest request = new StoreSaverRequest(
                9994,
                "Store #9994",
                "2024/12/25",
                "2025/01/06",
                "Spring 2024"
        );

        assertThatStoreCannotBeCreated(
                request,
                "Unable to determine type of store to create: all dates are present");
    }

    @Test
    public void reports_when_a_request_to_create_a_closed_store_does_not_include_opening_date() {
        StoreSaverRequest request = new StoreSaverRequest(
                9993,
                "Store #9993",
                "",
                "2025/01/06",
                ""
        );

        assertThatStoreCannotBeCreated(
                request,
                "A closed store must have an opening date");
    }

    @Test
    public void reports_when_a_request_to_create_an_expected_opening_store_does_include_closing_date() {
        StoreSaverRequest request = new StoreSaverRequest(
                9992,
                "Store #9992",
                "",
                "2025/01/06",
                "Autumn 1972"
        );

        assertThatStoreCannotBeCreated(
                request,
                "An expected opening store must not have a closing date");
    }

    @Test
    public void reports_when_a_request_to_create_an_expected_opening_store_does_include_opening_date() {
        StoreSaverRequest request = new StoreSaverRequest(
                9991,
                "Store #9991",
                "2024/12/25",
                "",
                "Spring 2024"
        );

        assertThatStoreCannotBeCreated(
                request,
                "An expected opening store must not have an opening date");
    }

    @Test
    public void reports_when_a_request_to_create_a_closed_store_has_the_closing_date_prior_to_the_opening_date() {
        StoreSaverRequest request = new StoreSaverRequest(
                9990,
                "Store #9990",
                "2025/01/06",
                "2025/01/05",
                ""
        );

        assertThatStoreCannotBeCreated(
                request,
                "Closing date cannot be before opening date");
    }


    private static void assertThatStoreCanBeCreated(StoreSaverRequest request) {
        Either<String, Store> storeOrError = StoreFactory.createStore(request);

        assertThat(storeOrError.isRight()).isTrue();
    }

    private static void assertThatStoreCannotBeCreated(StoreSaverRequest request, String expectedErrorMessage) {
        Either<String, Store> storeOrError = StoreFactory.createStore(request);

        assertThat(storeOrError.isLeft()).isTrue();
        assertThat(storeOrError.getLeft()).isEqualTo(expectedErrorMessage);
    }
}
