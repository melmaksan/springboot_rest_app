package com.epam.esm.certificate_service.exeption_handling;

public abstract class MainCertificateException extends RuntimeException {

    private String serviceCode;

    public MainCertificateException(String message, String code) {
        super(message);
        this.serviceCode = code;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}
