package domain;

public class InvalidStoreSaverRequestException extends RuntimeException {
    public InvalidStoreSaverRequestException(String reason) {
        super(String.format("Store cannot be saved (%s)", reason));
    }
}
