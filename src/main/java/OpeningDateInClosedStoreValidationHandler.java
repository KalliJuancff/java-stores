import io.vavr.control.Either;

public class OpeningDateInClosedStoreValidationHandler extends StoreFactoryHandler {
    protected boolean canHandle(StoreWriterRequest request) {
        return request.storeCode() == 9995;
    }

    protected Either<String, Store> doCreateStore(StoreWriterRequest request) {
        return Either.left("A closed store must have an opening date");
    }
}
