import io.vavr.control.Either;

public class StoreFactory {
    public static Either<String, Store> createStore(StoreWriterRequest request) {
        StoreFactoryHandler handler = firstHandler();
        return handler.createStore(request);
    }

    private static StoreFactoryHandler firstHandler() {
        StoreFactoryHandler h1 = new SomeDateValidationHandler();
        StoreFactoryHandler h2 = new NoAllDatesValidationHandler();
        StoreFactoryHandler h3 = new OpeningDateInClosedStoreValidationHandler();
        StoreFactoryHandler h4 = new NoClosingDateInExpectedOpeningStoreValidationHandler();
        StoreFactoryHandler h5 = new NoOpeningDateInExpectedOpeningStoreValidationHandler();
        StoreFactoryHandler h6 = new OpenStoreCreationHandler();
        StoreFactoryHandler h7 = new ClosedStoreCreationHandler();
        StoreFactoryHandler h8 = new ExpectedOpeningStoreCreationHandler();

        h1.setNext(h2);
        h2.setNext(h3);
        h3.setNext(h4);
        h4.setNext(h5);
        h5.setNext(h6);
        h6.setNext(h7);
        h7.setNext(h8);

        return h1;
    }
}
