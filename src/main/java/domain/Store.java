package domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class Store {
    private final int code;
    private final String name;
    private final LocalDate openingDate;
    private final LocalDate closingDate;
    private final String expectedOpeningDate;
    private final StoreState status;

    private Store(int code, String name, LocalDate openingDate, LocalDate closingDate, String expectedOpeningDate, StoreState status) {
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
                openingDate,
                null,
                "",
                StoreState.OPEN
        );
    }

    public static Store createAsClosed(int code, String name, LocalDate openingDate, LocalDate closingDate) {
        return new Store(
                code,
                name,
                openingDate,
                closingDate,
                "",
                StoreState.CLOSED
        );
    }

    public static Store createAsExpectedOpening(int code, String name, String expectedOpeningDate) {
        return new Store(
                code,
                name,
                null,
                null,
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
        return Optional.ofNullable(openingDate);
    }

    public Optional<LocalDate> closingDate() {
        return Optional.ofNullable(closingDate);
    }

    public String expectedOpeningDate() {
        return expectedOpeningDate;
    }

    public StoreState status() {
        return status;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return code == store.code;
    }

    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return "Store{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", openingDate=" + openingDate +
                ", closingDate=" + closingDate +
                ", expectedOpeningDate='" + expectedOpeningDate + '\'' +
                ", status=" + status +
                '}';
    }
}
