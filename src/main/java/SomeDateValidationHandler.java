import io.vavr.control.Either;

public class SomeDateValidationHandler extends StoreFactoryHandler {
    protected boolean canHandle(StoreWriterRequest request) {
        return request.storeOpeningDate().isEmpty() && request.storeClosingDate().isEmpty() && request.storeExpectedOpeningDate().isEmpty();
    }

    protected Either<String, Store> doCreateStore(StoreWriterRequest request) {
        return Either.left("Unable to determine type of store to create");
    }
}
