package domain;

import io.vavr.control.Either;

public abstract class StoreFactoryHandler {
    private StoreFactoryHandler next;

    public void setNext(StoreFactoryHandler next) {
        this.next = next;
    }

    public Either<String, Store> createStore(StoreSaverRequest request) {
        if (canHandle(request)) {
            return this.doCreateStore(request);
        }

        return next.createStore(request);
    }

    protected abstract boolean canHandle(StoreSaverRequest request);
    protected abstract Either<String, Store> doCreateStore(StoreSaverRequest request);
}
