package com.example.demo;

import org.springframework.http.HttpStatus;

/**
 * @author Kisong
 */
public enum ServiceError {

    METHOD_ARGUMENT_NOT_VALID (HttpStatus.BAD_REQUEST, 40000, "Provided argument validation error."),

    EMAIL_ALREADY_EXIST (HttpStatus.BAD_REQUEST, 40001, "Provided email address already exist."),

    ACCOUNT_ALREADY_EXIST (HttpStatus.BAD_REQUEST, 40002, "Provided account already exist."),

    ACCOUNT_HOLDER_NAME_MISMATCH (HttpStatus.BAD_REQUEST, 40003, "Provided account holder name mismatch."),

    INVALID_PARAMETER (HttpStatus.BAD_REQUEST, 40004, "Provided parameter invalid."),

    NO_SUCH_USER (HttpStatus.NOT_FOUND, 40400, "Provided member id not found."),

    NO_SUCH_ACCOUNT (HttpStatus.NOT_FOUND, 40401, "Provided account id/number not found."),

    NO_SUCH_BANK (HttpStatus.NOT_FOUND, 40402, "Provided bank code/swift not found."),

    INTERNAL_SERVER_ERROR (HttpStatus.INTERNAL_SERVER_ERROR, 50000, "Internal server error occurred."),

    EXTERNAL_SYSTEM_ERROR (HttpStatus.INTERNAL_SERVER_ERROR, 50001, "External system error occurred.");

    private HttpStatus httpStatus;

    private int code;

    private String message;

    ServiceError(final HttpStatus httpStatus, final int code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    /**
     * Gets the assigned Http status of this ServiceError.
     *
     * @return instance of HttpStatus
     */
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    /**
     * Gets the assigned error code of this ServiceError.
     *
     * @return error code
     */
    public int code() {
        return code;
    }

    /**
     * Gets the assigned error message of this ServiceError.
     *
     * @return error message
     */
    public String message() {
        return message;
    }

}
