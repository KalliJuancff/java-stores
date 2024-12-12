import io.vavr.control.Either;

public class StoreFactory {
    public static Either<String, Store> createStore(StoreWriterRequest request) {
        StoreFactoryHandler handler = firstHandler();
        return handler.createStore(request);
    }

    private static StoreFactoryHandler firstHandler() {
        StoreFactoryHandler h1 = new storeCode9995Handler();
        StoreFactoryHandler h2 = new storeCode9994Handler();
        StoreFactoryHandler h3 = new storeCode9993Handler();
        StoreFactoryHandler h4 = new storeCode9992Or9991Handler();
        StoreFactoryHandler h5 = new expectedOpenStoreHandler();
        StoreFactoryHandler h6 = new openStoreHandler();
        StoreFactoryHandler h7 = new closedStoreHandler();

        h1.setNext(h2);
        h2.setNext(h3);
        h3.setNext(h4);
        h4.setNext(h5);
        h5.setNext(h6);
        h6.setNext(h7);

        return h1;
    }
}
