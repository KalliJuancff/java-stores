package cli;

import application.StoreSaver;
import domain.InvalidStoreSaverRequestException;
import domain.Store;
import domain.StoreRepository;
import domain.StoreSaverRequest;
import persistence.AppConfig;
import persistence.JpaStoreRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Stream;

public class CLI {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        StoreSaver storeSaver = context.getBean(StoreSaver.class);  // Use Case
        StoreRepository repository = context.getBean(StoreRepository.class);

        boolean exit;
        do {
            System.out.println("=======================================");
            System.out.println("WELCOME TO THE STORE MANAGEMENT SYSTEM!");
            System.out.println("Stores in the system:");
            Stream<Store> stores = repository.searchAll();
            stores.forEach(System.out::println);
            System.out.println("=======================================");
            System.out.println();

            try {
                StoreSaverRequest request = createRequestFromKeyboard();
                storeSaver.save(request);
                exit = false;
            } catch (InvalidStoreSaverRequestException e) {
                System.out.println("DOMAIN ERROR: invalid request provided ('" + e.getMessage() + "')");
                exit = true;
            } catch (RuntimeException e) {
                System.out.println("RUNTIME ERROR: invalid request provided ('" + e.getMessage() + "')");
                exit = true;
            }
        } while (!exit);

        closeRepositoryIfApplicable(repository);
    }

    private static StoreSaverRequest createRequestFromKeyboard() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the store code:");
        int code = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character ("mandatory")

        System.out.println("Enter the store name:");
        String name = scanner.nextLine();

        System.out.println("Enter the opening date in format YYYY-MM-DD:");
        String openingDate = scanner.nextLine();

        System.out.println("Enter the closing date in format YYYY-MM-DD:");
        String closingDate = scanner.nextLine();

        System.out.println("Enter the expected opening date in text format");
        String expectedOpeningDate = scanner.nextLine();

        // WARNING:
        // cannot close the scanner because it will close System.in and it will be impossible to read from it again
        //     scanner.close();

        return new StoreSaverRequest(
                code,
                name,
                openingDate,
                closingDate,
                expectedOpeningDate);
    }

    private static void closeRepositoryIfApplicable(StoreRepository repository) {
        if (repository instanceof JpaStoreRepository) {
            ((JpaStoreRepository) repository).close();
        }
    }
}
