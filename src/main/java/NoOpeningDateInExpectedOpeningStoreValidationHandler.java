import io.vavr.control.Either;

public class NoOpeningDateInExpectedOpeningStoreValidationHandler extends StoreFactoryHandler {
    public boolean canHandle(StoreWriterRequest request) {
        return request.storeCode() == 9993;
    }

    public Either<String, Store> doCreateStore(StoreWriterRequest request) {
        return Either.left("An expected opening store must not have an opening date");
    }
}
