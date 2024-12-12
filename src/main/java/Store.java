import java.time.LocalDate;
import java.util.Optional;

public class Store {
    private final int code;
    private final String name;
    private final Optional<LocalDate> openingDate;
    private final Optional<LocalDate> closingDate;
    private final String expectedOpeningDate;
    private final StoreState status;

    private Store(int code, String name, Optional<LocalDate> openingDate, Optional<LocalDate> closingDate, String expectedOpeningDate, StoreState status) {
        this.code = code;
        this.name = name;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.expectedOpeningDate = expectedOpeningDate;
        this.status = status;
    }

    public static Store createAsOpened(int code, String name, LocalDate openingDate) {
        return new Store(
                code,
                name,
                Optional.of(openingDate),
                Optional.empty(),
                "",
                StoreState.OPEN
        );
    }

    public static Store createAsClosed(int code, String name, LocalDate openingDate, LocalDate closingDate) {
        return new Store(
                code,
                name,
                Optional.of(openingDate),
                Optional.of(closingDate),
                "",
                StoreState.CLOSED
        );
    }

    public static Store createAsExpectedOpening(int code, String name, String expectedOpeningDate) {
        return new Store(
                code,
                name,
                Optional.empty(),
                Optional.empty(),
                expectedOpeningDate,
                StoreState.EXPECTED_OPENING
        );
    }

    public int code() {
        return code;
    }

    public String name() {
        return name;
    }

    public Optional<LocalDate> openingDate() {
        return openingDate;
    }

    public Optional<LocalDate> closingDate() {
        return closingDate;
    }

    public String expectedOpeningDate() {
        return expectedOpeningDate;
    }

    public StoreState status() {
        return status;
    }
}
