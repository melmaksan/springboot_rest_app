package com.epam.esm.rest_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderDTO {

    private long id;
    private int price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime time;
    private CertificateDTO certificate;
}
