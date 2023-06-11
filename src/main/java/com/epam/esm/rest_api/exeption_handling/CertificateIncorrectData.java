package com.epam.esm.rest_api.exeption_handling;

public class CertificateIncorrectData {

    private String errorMessage;
    private int errorCode;

    public CertificateIncorrectData() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}
