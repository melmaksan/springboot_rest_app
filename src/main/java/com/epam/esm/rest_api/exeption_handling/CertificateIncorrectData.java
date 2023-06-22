package com.epam.esm.rest_api.exeption_handling;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CertificateIncorrectData {

    private String errorMessage;
    private int errorCode;
}
