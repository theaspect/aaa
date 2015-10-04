package com.blzr;

public enum ResultType {
    SUCCESS(0),
    UNKNOWN_LOGIN(1),
    INVALID_PASSWORD(2),
    ACCESS_DENIED(3);

    private final int code;

    ResultType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
