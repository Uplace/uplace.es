package com.arnaugarcia.uplace.web.rest.errors;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String ERR_BAD_ID = "error.badId";
    public static final String ERR_BAD_USER = "error.badUser";
    public static final String ERR_BAD_REFERENCE = "error.badReference";
    public static final String ERR_ID_EXISTS = "error.idExists";
    public static final String ERR_EMAIL = "error.email";
    public static final String ERR_BAD_TYPE = "error.badType";
    public static final String NOT_IMPLEMENTED = "error.notImplemented";
    public static final String PROBLEM_BASE_URL = "http://uplace.es/#/error";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    public static final URI PARAMETERIZED_TYPE = URI.create(PROBLEM_BASE_URL + "/parameterized");
    public static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password");
    public static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");
    public static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");
    public static final URI EMAIL_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/email-not-found");

    private ErrorConstants() {}

}
