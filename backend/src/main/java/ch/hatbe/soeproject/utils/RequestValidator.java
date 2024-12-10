package ch.hatbe.soeproject.utils;

public class RequestValidator {
    public static String validateSortDirection(String sort) {
        if ("ASC".equalsIgnoreCase(sort) || "DESC".equalsIgnoreCase(sort)) {
            return sort.toUpperCase();
        }
        return null;
    }
}
