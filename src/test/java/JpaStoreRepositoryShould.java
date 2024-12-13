import domain.Store;
import domain.StoreRepository;
import infrastructure.JpaStoreRepository;
import infrastructure.StoreModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class JpaStoreRepositoryShould {
    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("jpa-integration-tests");
        em = emf.createEntityManager();
    }

    @AfterEach
    public void tearDown() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    void should_insert_a_store_that_does_not_exist() {
        StoreRepository sut = new JpaStoreRepository();
        Store store = Store.createAsOpened(1,"Store 1", LocalDate.of(2025, 1, 1));

        sut.upsert(store);

        StoreModel storeModel = em.find(StoreModel.class, 1);
        assertThat(storeModel).isNotNull();
    }
}
