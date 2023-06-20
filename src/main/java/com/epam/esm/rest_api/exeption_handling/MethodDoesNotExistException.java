package com.epam.esm.rest_api.exeption_handling;

import com.epam.esm.certificate_service.exeption_handling.MainCertificateException;

public class MethodDoesNotExistException extends MainCertificateException {

    private static final String EXCEPTION_CODE = "5";

    public MethodDoesNotExistException(String message, String code) {
        super(message, EXCEPTION_CODE + code);
    }
}
