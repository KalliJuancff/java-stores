import application.StoreSaver;
import domain.Store;
import domain.StoreRepository;
import domain.StoreSaverRequest;
import infrastructure.AppConfig;
import infrastructure.JpaStoreRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

public class Application {
    public static void main(String[] args) {
        System.out.println("Starting Store Application!");

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
        stores.forEach(System.out::println);

        closeRepositoryIfApplicable(repository);

        System.out.println("Ending Store Application!");
    }

    private static void closeRepositoryIfApplicable(StoreRepository repository) {
        if (repository instanceof JpaStoreRepository) {
            System.out.println("Closing JPA repository");
            ((JpaStoreRepository) repository).close();
        } else {
            System.out.println("Skipping closing repository because is not a JPA repository");
        }
    }
}
