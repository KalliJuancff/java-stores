package infrastructure;

import domain.Store;
import domain.StoreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.stream.Stream;

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

    public Stream<Store> searchAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-integration-tests");
        EntityManager em = emf.createEntityManager();

        List<StoreModel> storeModels = em
                .createQuery("SELECT s FROM StoreModel s", StoreModel.class)
                .getResultList();
        Stream<Store> result = storeModels.stream().map(this::mapTo);

        em.close();
        emf.close();

        return result;
    }

    private Store mapTo(StoreModel storeModel) {
        if (storeModel.getExpectedOpeningDate() != null) {
            return Store.createAsExpectedOpening(
                    storeModel.getCode(),
                    storeModel.getName(),
                    storeModel.getExpectedOpeningDate());
        }
        if (storeModel.getClosingDate() != null) {
            return Store.createAsClosed(
                    storeModel.getCode(),
                    storeModel.getName(),
                    storeModel.getOpeningDate(),
                    storeModel.getClosingDate());
        }
        return Store.createAsOpened(
                storeModel.getCode(),
                storeModel.getName(),
                storeModel.getOpeningDate());
    }
}
