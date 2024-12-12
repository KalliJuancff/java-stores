import io.vavr.control.Either;

public abstract class StoreFactoryHandler {
    private StoreFactoryHandler next;

    public void setNext(StoreFactoryHandler next) {
        this.next = next;
    }

    public Either<String, Store> createStore(StoreWriterRequest request) {
        if (canHandle(request)) {
            return this.doCreateStore(request);
        }

        return next.createStore(request);
    }

    protected abstract boolean canHandle(StoreWriterRequest request);
    protected abstract Either<String, Store> doCreateStore(StoreWriterRequest request);
}
