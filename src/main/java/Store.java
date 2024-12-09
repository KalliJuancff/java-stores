import java.time.LocalDate;
import java.util.Optional;

public class Store {
    private final int code;
    private final String name;
    private final Optional<LocalDate> openingDate;
    private final Optional<LocalDate> closingDate;
    private final String expectedOpeningDate;

    public static Store createOpenStore(int code, String name, Optional<LocalDate> openingDate) {
        return new Store(code, name, openingDate, Optional.empty(), "");
    }

    private Store(int code, String name, Optional<LocalDate> openingDate, Optional<LocalDate> closingDate, String expectedOpeningDate) {
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
        return "OPEN";
    }
}
