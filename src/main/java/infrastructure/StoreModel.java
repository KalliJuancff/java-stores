package infrastructure;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Optional;

@Entity
public class StoreModel {
    @Id
    private int code;
    @Column(nullable = false)
    private String name;
    @Column(nullable = true)
    private LocalDate openingDate;
    @Column(nullable = true)
    private LocalDate closingDate;
    @Column(nullable = true)
    private String expectedOpeningDate;

    public StoreModel(int code, String name, Optional<LocalDate> openingDate, Optional<LocalDate> closingDate, String expectedOpeningDate) {
        this.code = code;
        this.name = name;
        this.openingDate = openingDate.orElse(null);
        this.closingDate = closingDate.orElse(null);
        this.expectedOpeningDate = expectedOpeningDate.isEmpty() ? null : expectedOpeningDate;
    }

    public StoreModel() {
        // Do-nothing (Hibernate requirement)
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    public String getExpectedOpeningDate() {
        return expectedOpeningDate;
    }

    public void setExpectedOpeningDate(String expectedOpeningDate) {
        this.expectedOpeningDate = expectedOpeningDate;
    }
}
