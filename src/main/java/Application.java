import application.StoreSaver;
import domain.Store;
import domain.StoreSaverRequest;
import infrastructure.JpaStoreRepository;

import java.util.stream.Stream;

public class Application {
    public static void main(String[] args) {
        System.out.println("Starting Store Application!");

        var repository = new JpaStoreRepository();
        var useCase = new StoreSaver(repository);
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

        repository.close();

        System.out.println("Ending Store Application!");
    }
}
