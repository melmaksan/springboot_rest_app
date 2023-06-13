package com.epam.esm.rest_api.dto;

import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.entities.User;
import com.epam.esm.rest_api.controllers.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class LinkAssembler {

    static void addLinksToCertificateDTO(GiftCertificate certificate, CertificateDTO certificateDTO) {
        try {
            certificateDTO.add(linkTo(GiftCertificateController.class,
                    GiftCertificateController.class.getMethod("getCertificateById", long.class),
                    certificate.getId()).withRel("show by id"));
            certificateDTO.add(linkTo(GiftCertificateController.class,
                    GiftCertificateController.class.getMethod("getGiftCertificateByName", String.class),
                    certificate.getName()).withRel("show by name"));
            certificateDTO.add(linkTo(GiftCertificateController.class,
                    GiftCertificateController.class.getMethod("deleteCertificate", long.class),
                    certificate.getId()).withRel("delete"));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    static void addLinksToTagDTO(Tag tag, TagDTO tagDTO) {
        try {
            tagDTO.add(linkTo(TagController.class, TagController.class.getMethod("showTag", int.class),
                    tag.getId()).withRel("show by id"));
            tagDTO.add(linkTo(TagController.class, TagController.class.getMethod("getGiftCertificatesByName",
                    String.class), tag.getName()).withRel("show by name"));
            tagDTO.add(linkTo(TagController.class, TagController.class.getMethod("deleteTag", int.class),
                    tag.getId()).withRel("delete"));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    static void addLinksToUserDTO(User user, UserDTO userDTO) {
        try {
            userDTO.add(linkTo(UserController.class, UserController.class.getMethod("getUserById",
                    long.class), user.getId()).withRel("show by id"));
            userDTO.add(linkTo(UserController.class, UserController.class.getMethod("getUserByName",
                    String.class), user.getFirstName()).withRel("show by name"));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }
}
