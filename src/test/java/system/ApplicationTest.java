package system;

import application.StoreSaver;
import domain.Store;
import domain.StoreSaverRequest;
import infrastructure.JpaStoreRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationTest {
    @Test
    public void test_entire_application_with_real_dependencies() {
        JpaStoreRepository repository = new JpaStoreRepository();
        StoreSaver useCase = new StoreSaver(repository);
        useCase.save(new StoreSaverRequest(
                1234,
                "Store 1234",
                "2025/12/25",
                "",
                ""));

        Stream<Store> stores = repository.searchAll();

        assertThat(stores).contains(Store.createAsOpened(1234, "Store 1234", LocalDate.of(2025, 12, 25)));
    }
}
