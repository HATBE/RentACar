package ch.hatbe.soeproject.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RequestValidatorTest {
    @Test
    public void testValidateSortDirection_ValidASC() {
        String input = "ASC";
        String result = RequestValidator.validateSortDirection(input);
        assertEquals("ASC", result, "Expected result to be 'ASC'");
    }

    @Test
    public void testValidateSortDirection_ValidDESC() {
        String input = "DESC";
        String result = RequestValidator.validateSortDirection(input);
        assertEquals("DESC", result, "Expected result to be 'DESC'");
    }

    @Test
    public void testValidateSortDirection_LowercaseASC() {
        String input = "asc";
        String result = RequestValidator.validateSortDirection(input);
        assertEquals("ASC", result, "Expected result to be 'ASC' when input is 'asc'");
    }

    @Test
    public void testValidateSortDirection_LowercaseDESC() {
        String input = "desc";
        String result = RequestValidator.validateSortDirection(input);
        assertEquals("DESC", result, "Expected result to be 'DESC' when input is 'desc'");
    }

    @Test
    public void testValidateSortDirection_InvalidInput() {
        String input = "INVALID";
        String result = RequestValidator.validateSortDirection(input);
        assertNull(result, "Expected result to be null for invalid input");
    }

    @Test
    public void testValidateSortDirection_NullInput() {
        String result = RequestValidator.validateSortDirection(null);
        assertNull(result, "Expected result to be null for null input");
    }
}