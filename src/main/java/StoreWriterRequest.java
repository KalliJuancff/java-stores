public record StoreWriterRequest(
        int storeCode,
        String storeName,
        String storeOpeningDate,
        String storeClosingDate,
        String storeExpectedOpeningDate) {
}
