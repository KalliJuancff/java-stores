import io.vavr.control.Either;

public class StoreFactory {
    public static Either<String, Store> createStore(StoreWriterRequest request) {
        StoreFactoryHandler handler = firstHandler();
        return handler.createStore(request);
    }

    private static StoreFactoryHandler firstHandler() {
        StoreFactoryHandler h1 = new OpeningDateInClosedStoreValidationHandler();
        StoreFactoryHandler h2 = new NoClosingDateInExpectedOpeningStoreValidationHandler();
        StoreFactoryHandler h3 = new NoOpeningDateInExpectedOpeningStoreValidationHandler();
        StoreFactoryHandler h4 = new NoAllDatesValidationHandler();
        StoreFactoryHandler h5 = new SomeDateValidationHandler();
        StoreFactoryHandler h6 = new OpenStoreHandler();
        StoreFactoryHandler h7 = new ClosedStoreHandler();

        h1.setNext(h2);
        h2.setNext(h3);
        h3.setNext(h4);
        h4.setNext(h5);
        h5.setNext(h6);
        h6.setNext(h7);

        return h1;
    }
}
