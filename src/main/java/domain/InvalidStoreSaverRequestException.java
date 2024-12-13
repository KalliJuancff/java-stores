package domain;

public class InvalidStoreSaverRequestException extends RuntimeException {
    public InvalidStoreSaverRequestException(String reason) {
        super(String.format("domain.Store cannot be saved (%s)", reason));
    }
}
