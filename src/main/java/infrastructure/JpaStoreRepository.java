package infrastructure;

import domain.Store;
import domain.StoreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaStoreRepository implements StoreRepository {
    public void upsert(Store store) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-integration-tests");
        EntityManager em = emf.createEntityManager();

        StoreModel storeModel = mapFrom(store);

        em.getTransaction().begin();
        em.merge(storeModel);
        em.getTransaction().commit();

        em.close();
        emf.close();
    }

    private StoreModel mapFrom(Store store) {
        return new StoreModel(
                store.code(),
                store.name(),
                store.openingDate().orElse(null),
                store.closingDate().orElse(null),
                store.expectedOpeningDate().isEmpty() ? null : store.expectedOpeningDate());
    }
}
