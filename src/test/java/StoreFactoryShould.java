import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StoreFactoryShould {
    @Test
    public void create_an_open_store() {
        StoreWriterRequest request = new StoreWriterRequest(
                9999,
                "Store #9999",
                "2025/12/25",
                "",
                ""
        );

        Store store = StoreFactory.createStore(request);

        assertThat(store.status()).isEqualTo("OPEN");
    }

    @Test
    public void create_a_closed_store() {
        StoreWriterRequest request = new StoreWriterRequest(
                9999,
                "Store #9999",
                "2024/12/25",
                "2025/01/06",
                ""
        );

        Store store = StoreFactory.createStore(request);

        assertThat(store.status()).isEqualTo("CLOSED");
    }
}
