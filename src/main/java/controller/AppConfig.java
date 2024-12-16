package controller;

import application.StoreSaver;
import domain.StoreRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import persistence.JsonStoreRepository;

@Configuration
public class AppConfig {
    @Bean
    public StoreRepository storeRepository() {
        return new JsonStoreRepository("stores.json");
    }

    @Bean
    public StoreSaver storeSaver(StoreRepository storeRepository) {
        return new StoreSaver(storeRepository);
    }
}
