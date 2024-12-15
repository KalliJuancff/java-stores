import application.StoreSaver;
import domain.Store;
import domain.StoreRepository;
import domain.StoreSaverRequest;
import infrastructure.InMemoryStoreRepository;

import java.util.stream.Stream;

public class Application {
    public static void main(String[] args) {
        System.out.println("Starting Store Application!");

        StoreRepository repository = new InMemoryStoreRepository();
        var useCase = new StoreSaver(repository);
        useCase.save(new StoreSaverRequest(
                1234,
                "Store 1234",
                "2025/12/25",
                "",
                ""));

        Stream<Store> stores = repository.searchAll();
        stores.forEach(System.out::println);

        System.out.println("Ending Store Application!");
    }
}
