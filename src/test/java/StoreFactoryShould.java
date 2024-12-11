import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StoreFactoryShould {
    @Test
    public void create_an_open_store() {
        StoreWriterRequest request = new StoreWriterRequest(
                1000,
                "Store #1000",
                "2003/12/25",
                "",
                ""
        );

        Store store = StoreFactory.createStore(request);

        assertThat(store).isNotNull();
    }
}
