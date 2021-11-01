package com.fatemeh.app.exception;

public enum ErrorMessagesEnum {

    MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
    ILLEGAL_OFFSET("Offset must not be less than zero"),
    ILLEGAL_LIMIT("Limit must be greater than one"),
    SETTING_NOT_FOUND("Setting with provided id is not found"),
    MODEL_NOT_FOUND("Jukebox model does not exist"),
    COULD_NOT_READ_JSON_RESOURCE("Could not read data from json file"),
    RECORD_IS_NULL("Record is null");


    private String errorMessage;

    ErrorMessagesEnum(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
