package infrastructure;

import domain.Store;
import domain.StoreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class JpaStoreRepository implements StoreRepository {
    public void upsert(Store store) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-integration-tests");
        EntityManager em = emf.createEntityManager();

        StoreModel storeModel = mapFrom(store);

        em.getTransaction().begin();
        em.persist(storeModel);
        em.getTransaction().commit();

        em.close();
        emf.close();
    }

    private StoreModel mapFrom(Store store) {
        // TODO: FIX this (openingDate and closingDate are always sent as now)
        return new StoreModel(store.code(), store.name(), LocalDate.now(), LocalDate.now(), store.expectedOpeningDate());
    }
}