package system;

import application.StoreSaver;
import domain.Store;
import domain.StoreRepository;
import domain.StoreSaverRequest;
import infrastructure.AppConfig;
import infrastructure.JpaStoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class CliTest {
    @Test
    public void test_entire_application_with_real_dependencies() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        StoreSaver useCase = context.getBean(StoreSaver.class);
        StoreRepository repository = context.getBean(StoreRepository.class);
        useCase.save(new StoreSaverRequest(
                1234,
                "Store 1234",
                "2025/12/25",
                "",
                ""));
        useCase.save(new StoreSaverRequest(
                1238,
                "Store 1238",
                "2014/01/14",
                "",
                ""));
        useCase.save(new StoreSaverRequest(
                1240,
                "Store 1240",
                "",
                "",
                "2025"));
        useCase.save(new StoreSaverRequest(
                1234,
                "Store 1234 [Moved]",
                "2025/12/25",
                "2026/01/06",
                ""));

        Stream<Store> stores = repository.searchAll();

        assertThat(stores).contains(
                Store.createAsClosed(1234, "Store 1234 [Moved]", LocalDate.of(2025, 12, 25), LocalDate.of(2026, 1, 6)),
                Store.createAsOpened(1238, "Store 1238", LocalDate.of(2014, 1, 14)),
                Store.createAsExpectedOpening(1240, "Store 1240", "2025"));

        closeRepositoryIfApplicable(repository);
    }

    private static void closeRepositoryIfApplicable(StoreRepository repository) {
        if (repository instanceof JpaStoreRepository) {
            ((JpaStoreRepository) repository).close();
        }
    }
}
