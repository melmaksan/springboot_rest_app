package com.epam.esm.rest_api.exeption_handling;

import com.epam.esm.certificate_service.exeption_handling.MainCertificateException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.DuplicateDataException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.EmptyRequestBodyException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.NoSuchDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoSuchDataException.class, EmptyRequestBodyException.class, DuplicateDataException.class})
    public ResponseEntity<CertificateIncorrectData> handleCertificateException(MainCertificateException exception) {
        CertificateIncorrectData data = new CertificateIncorrectData();
        data.setErrorMessage(exception.getMessage());
        data.setErrorCode(Integer.parseInt(HttpStatus.BAD_REQUEST.value() + exception.getServiceCode()));

        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<CertificateIncorrectData> handleAllException(Exception exception){
//        CertificateIncorrectData data = new CertificateIncorrectData();
//        data.setErrorMessage(exception.getMessage());
//        data.setErrorCode(HttpStatus.BAD_REQUEST.value());
//
//        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
//    }

}
