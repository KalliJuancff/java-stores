package persistence;

import jakarta.persistence.*;

import java.time.LocalDate;

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

    public StoreModel(int code, String name, LocalDate openingDate, LocalDate closingDate, String expectedOpeningDate) {
        this.code = code;
        this.name = name;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.expectedOpeningDate = expectedOpeningDate;
    }

    public StoreModel() {
        // Do-nothing (Hibernate requirement)
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public String getExpectedOpeningDate() {
        return expectedOpeningDate;
    }
}
