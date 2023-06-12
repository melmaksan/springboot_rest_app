package com.epam.esm.rest_api.dto;

import com.epam.esm.certificate_service.entities.*;
import com.epam.esm.rest_api.controllers.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class Mapper {

    public CertificateDTO toCertificateDto(GiftCertificate certificate) {
        String name = certificate.getName();
        String description = certificate.getDescription();
        int price = certificate.getPrice();
        int duration = certificate.getDuration();
        LocalDate createDate = certificate.getCreateDate().toLocalDate();
        List<String> tags = new ArrayList<>();
        if (certificate.getTags() != null) {
            tags = certificate.getTags().stream().map(Tag::getName).collect(Collectors.toList());
        }

        CertificateDTO certificateDTO = new CertificateDTO(name, description, price, duration, createDate, tags);
        try {
            certificateDTO.add(linkTo(methodOn(GiftCertificateController.class)
                    .getCertificateById(certificate.getId())).withSelfRel());
            certificateDTO.add(linkTo(GiftCertificateController.class,
                    GiftCertificateController.class.getMethod("deleteCertificate", long.class),
                    certificate.getId()).withRel("delete"));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
        return certificateDTO;
    }

    public TagDTO toTagDto(Tag tag) {
        TagDTO tagDTO = new TagDTO(tag.getName());
        try {
            tagDTO.add(linkTo(methodOn(TagController.class).showTag(tag.getId())).withSelfRel());
            tagDTO.add(linkTo(TagController.class,
                    TagController.class.getMethod("deleteTag", int.class), tag.getId()).withRel("delete"));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
        return tagDTO;
    }

    public UserDTO toUserDto(User user) {
        String name = user.getFirstName();
        String surname = user.getSurname();
        String email = user.getEmail();

        return new UserDTO(name, surname, email)
                .add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
    }

    public OrderDTO toOrderDto(Order order) {
        int price = order.getPrice();
        LocalDateTime dateTime = order.getTime();
        CertificateDTO certificateDTO = toCertificateDto(order.getCertificate());

        return new OrderDTO(price, dateTime, certificateDTO);
    }
}
