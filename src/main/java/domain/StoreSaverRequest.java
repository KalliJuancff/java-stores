package domain;

import java.time.LocalDate;

public record StoreSaverRequest(
        int storeCode,
        String storeName,
        String storeOpeningDate,
        String storeClosingDate,
        String storeExpectedOpeningDate
) {
    public LocalDate storeOpeningDateAsLocalDate() {
        return LocalDate.parse(storeOpeningDate().replace("/", "-"));
    }

    public LocalDate storeClosingDateAsLocalDate() {
        return LocalDate.parse(storeClosingDate().replace("/", "-"));
    }
}
