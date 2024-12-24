package ch.hatbe.soeproject.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ErrorResponse {
    private String message;
    private String code;
    private List<String> errors;

    public ErrorResponse(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public ErrorResponse(String message, String code, List<String> errors) {
        this.message = message;
        this.code = code;
        this.errors = errors;
    }
}