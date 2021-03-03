package com.avinty.hr.exception;

public class ResourceException extends Exception {

    private final String type;

    public ResourceException(String type, String message) {
        super(message);
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
