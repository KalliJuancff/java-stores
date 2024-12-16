package cli;

import application.StoreSaver;
import domain.StoreRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import persistence.JpaStoreRepository;

@Configuration
public class AppConfig {
    @Bean
    public StoreRepository storeRepository() {
        return new JpaStoreRepository();
    }

    @Bean
    public StoreSaver storeSaver(StoreRepository storeRepository) {
        return new StoreSaver(storeRepository);
    }
}
