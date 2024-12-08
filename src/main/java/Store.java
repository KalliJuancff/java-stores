import java.time.LocalDate;
import java.util.Optional;

public class Store {
    private final int code;
    private final String name;
    private final Optional<LocalDate> openingDate;
    private final Optional<LocalDate> closingDate;
    private final String expectedOpeningDate;

    public Store(int code, String name, Optional<LocalDate> openingDate, Optional<LocalDate> closingDate, String expectedOpeningDate) {
        this.code = code;
        this.name = name;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.expectedOpeningDate = expectedOpeningDate;
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

    public String  expectedOpeningDate() {
        return expectedOpeningDate;
    }

    public String status() {
        if (openingDate.isEmpty() && closingDate.isEmpty()) {
            return "EXPECTED_OPENING";
        }

        if (closingDate.isPresent()) {
            return "CLOSED";
        }

        return "OPEN";
    }
}
