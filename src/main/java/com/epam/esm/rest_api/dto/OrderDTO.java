package com.epam.esm.rest_api.dto;

import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Objects;

public class OrderDTO extends RepresentationModel<OrderDTO> {

    private int price;
    private LocalDateTime time;
    private CertificateDTO certificate;

    public OrderDTO(int price, LocalDateTime time, CertificateDTO certificate) {
        this.price = price;
        this.time = time;
        this.certificate = certificate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public CertificateDTO getCertificate() {
        return certificate;
    }

    public void setCertificate(CertificateDTO certificate) {
        this.certificate = certificate;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "price=" + price +
                ", time=" + time +
                ", certificateDTO=" + certificate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return price == orderDTO.price && Objects.equals(time, orderDTO.time) &&
                Objects.equals(certificate, orderDTO.certificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, time, certificate);
    }
}
