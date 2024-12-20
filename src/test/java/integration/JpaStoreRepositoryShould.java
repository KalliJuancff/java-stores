package integration;

import domain.Store;
import domain.StoreRepository;
import persistence.JpaStoreRepository;
import persistence.StoreModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class JpaStoreRepositoryShould {
    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("jpa-integration-tests");
        em = emf.createEntityManager();

        em.getTransaction().begin();
        em.createQuery("DELETE FROM StoreModel").executeUpdate();
        em.getTransaction().commit();
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
    public void insert_an_open_store_that_does_not_exist() {
        StoreRepository sut = new JpaStoreRepository();
        Store store = Store.createAsOpened(
                1,
                "Store #1",
                LocalDate.of(2025, 1, 1));

        sut.upsert(store);

        StoreModel storeModel = em.find(StoreModel.class, 1);
        assertThat(storeModel).isNotNull();
        assertThat(storeModel.getCode()).isEqualTo(1);
        assertThat(storeModel.getName()).isEqualTo("Store #1");
        assertThat(storeModel.getOpeningDate()).isEqualTo(LocalDate.of(2025, 1, 1));
        assertThat(storeModel.getClosingDate()).isNull();
        assertThat(storeModel.getExpectedOpeningDate()).isNull();
    }

    @Test
    public void insert_a_closing_store_that_does_not_exist() {
        StoreRepository sut = new JpaStoreRepository();
        Store store = Store.createAsClosed(
                2,
                "Store #2",
                LocalDate.of(2025, 12, 23),
                LocalDate.of(2025, 12, 25));

        sut.upsert(store);

        StoreModel storeModel = em.find(StoreModel.class, 2);
        assertThat(storeModel).isNotNull();
        assertThat(storeModel.getCode()).isEqualTo(2);
        assertThat(storeModel.getName()).isEqualTo("Store #2");
        assertThat(storeModel.getOpeningDate()).isEqualTo(LocalDate.of(2025, 12, 23));
        assertThat(storeModel.getClosingDate()).isEqualTo(LocalDate.of(2025, 12, 25));
        assertThat(storeModel.getExpectedOpeningDate()).isNull();
    }

    @Test
    public void insert_an_expected_opening_store_that_does_not_exist() {
        StoreRepository sut = new JpaStoreRepository();
        Store store = Store.createAsExpectedOpening(
                3,
                "Store #3",
                "January 2014");

        sut.upsert(store);

        StoreModel storeModel = em.find(StoreModel.class, 3);
        assertThat(storeModel).isNotNull();
        assertThat(storeModel.getCode()).isEqualTo(3);
        assertThat(storeModel.getName()).isEqualTo("Store #3");
        assertThat(storeModel.getOpeningDate()).isNull();
        assertThat(storeModel.getClosingDate()).isNull();
        assertThat(storeModel.getExpectedOpeningDate()).isEqualTo("January 2014");
    }


    @Test
    public void update_a_store_that_already_exists() {
        StoreRepository sut = new JpaStoreRepository();
        Store oldStore = Store.createAsExpectedOpening(
                4,
                "Store #4",
                "December 2003");
        sut.upsert(oldStore);
        Store newStore = Store.createAsExpectedOpening(
                4,
                "Store #4 (reformed)",
                "December 2014");

        sut.upsert(newStore);

        StoreModel storeModel = em.find(StoreModel.class, 4);
        assertThat(storeModel.getName()).isEqualTo("Store #4 (reformed)");
        assertThat(storeModel.getExpectedOpeningDate()).isEqualTo("December 2014");
    }


    @Test
    public void fetch_all_stores() {
        StoreRepository sut = new JpaStoreRepository();
        Store store1 = Store.createAsOpened(
                1,
                "Store #1",
                LocalDate.of(2025, 1, 1));
        Store store2 = Store.createAsClosed(
                2,
                "Store #2",
                LocalDate.of(2025, 12, 23),
                LocalDate.of(2025, 12, 25));
        Store store3 = Store.createAsExpectedOpening(
                3,
                "Store #3",
                "January 2014");
        sut.upsert(store1);
        sut.upsert(store2);
        sut.upsert(store3);

        Stream<Store> stores = sut.searchAll();

        assertThat(stores).containsExactlyInAnyOrder(store1, store2, store3);
    }
}
