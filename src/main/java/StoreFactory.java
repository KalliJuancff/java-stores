import io.vavr.control.Either;

public class StoreFactory {
    static Either<String, Store> createStore(StoreWriterRequest request) {
        if (request.storeCode() == 9995) {
            return new storeCode9995Handler().createStore(request);
        }
        if (request.storeCode() == 9994) {
            return new storeCode9994Handler().createStore(request);
        }
        if (request.storeCode() == 9993) {
            return new storeCode9993Handler().createStore(request);
        }
        if (request.storeCode() == 9992 || request.storeCode() == 9991) {
            return new storeCode9992Handler().createStore(request);
        }

        if (request.storeOpeningDate().isEmpty() && request.storeClosingDate().isEmpty()) {
            return new expectedOpenStoreHandler().createStore(request);
        } else if (request.storeClosingDate().isEmpty()) {
            return new openStoreHandler().createStore(request);
        }
        return new closedStoreHandler().createStore(request);
    }
}
