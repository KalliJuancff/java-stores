public record StoreSaverRequest(
        int storeCode,
        String storeName,
        String storeOpeningDate,
        String storeClosingDate,
        String storeExpectedOpeningDate) {
}
