package com.epam.esm.rest_api.dto;

import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.entities.User;
import com.epam.esm.certificate_service.service.GiftCertificateService;
import com.epam.esm.certificate_service.service.TagService;
import com.epam.esm.certificate_service.service.UserService;
import com.epam.esm.rest_api.controllers.*;
import com.epam.esm.rest_api.exeption_handling.MethodDoesNotExistException;
import org.springframework.stereotype.Component;

import static com.epam.esm.rest_api.Constants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class LinkAssembler {

    private static final String CODE = "04";

    private static UserService userService;
    private static TagService tagService;
    private static GiftCertificateService certificateService;

    public LinkAssembler(UserService userService, TagService tagService, GiftCertificateService certificateService) {
        LinkAssembler.userService = userService;
        LinkAssembler.tagService = tagService;
        LinkAssembler.certificateService = certificateService;
    }

    /**
     * This method adds links to certificate DTO
     *
     * @param certificate entity that will be converted
     * @param certificateDTO DTO to which links are added
     */
    static void addLinksToCertificateDTO(GiftCertificate certificate, CertificateDTO certificateDTO) {
        int rows = certificateService.getNumberOfRows();
        int size = Integer.parseInt(DEFAULT_CERTIFICATE_PAGE_SIZE);
        try {
            certificateDTO.add(linkTo(GiftCertificateController.class,
                    GiftCertificateController.class.getMethod("getCertificateById", long.class),
                    certificate.getId()).withRel(SHOW_BY_ID));
            certificateDTO.add(linkTo(GiftCertificateController.class,
                    GiftCertificateController.class.getMethod("getCertificateByName", String.class),
                    certificate.getName()).withRel(SHOW_BY_NAME));
            certificateDTO.add(linkTo(GiftCertificateController.class,
                    GiftCertificateController.class.getMethod("deleteCertificate", long.class),
                    certificate.getId()).withRel(DELETE));
            certificateDTO.add(linkTo(GiftCertificateController.class,
                    GiftCertificateController.class.getMethod("getAllCertificates", int.class, int.class),
                    Integer.parseInt(DEFAULT_TAG_PAGE_NUMBER), size).withRel(FIRST_PAGE));
            certificateDTO.add(linkTo(GiftCertificateController.class,
                    GiftCertificateController.class.getMethod("getAllCertificates", int.class, int.class),
                    getLastPage(rows, size), size).withRel(LAST_PAGE));
        } catch (NoSuchMethodException e) {
            throw new MethodDoesNotExistException(e.getMessage(), CODE);
        }
    }

    /**
     * This method adds links to tag DTO
     *
     * @param tag entity that will be converted
     * @param tagDTO DTO to which links are added
     */
    static void addLinksToTagDTO(Tag tag, TagDTO tagDTO) {
        int rows = tagService.getNumberOfRows();
        int size = Integer.parseInt(DEFAULT_TAG_PAGE_SIZE);
        try {
            tagDTO.add(linkTo(TagController.class, TagController.class.getMethod("getTagById", int.class),
                    tag.getId()).withRel(SHOW_BY_ID));
            tagDTO.add(linkTo(TagController.class, TagController.class.getMethod("getTagByName",
                    String.class), tag.getName()).withRel(SHOW_BY_NAME));
            tagDTO.add(linkTo(TagController.class, TagController.class.getMethod("deleteTag", int.class),
                    tag.getId()).withRel(DELETE));
            tagDTO.add(linkTo(TagController.class, TagController.class.getMethod("getAllTags",
                    int.class, int.class), Integer.parseInt(DEFAULT_TAG_PAGE_NUMBER), size).withRel(FIRST_PAGE));
            tagDTO.add(linkTo(TagController.class, TagController.class.getMethod("getAllTags",
                    int.class, int.class), getLastPage(rows, size), size).withRel(LAST_PAGE));
        } catch (NoSuchMethodException e) {
            throw new MethodDoesNotExistException(e.getMessage(), CODE);
        }
    }

    /**
     * This method adds links to user DTO
     *
     * @param user entity that will be converted
     * @param userDTO DTO to which links are added
     */
    static void addLinksToUserDTO(User user, UserDTO userDTO) {
        int rows = userService.getNumberOfRows();
        int size = Integer.parseInt(DEFAULT_USER_PAGE_SIZE);
        try {
            userDTO.add(linkTo(UserController.class, UserController.class.getMethod("getUserById",
                    long.class), user.getId()).withRel(SHOW_BY_ID));
            userDTO.add(linkTo(UserController.class, UserController.class.getMethod("getUserByName",
                    String.class), user.getFirstName()).withRel(SHOW_BY_NAME));
            userDTO.add(linkTo(UserController.class, UserController.class.getMethod("getAllUsers",
                    int.class, int.class), Integer.parseInt(DEFAULT_USER_PAGE_NUMBER), size).withRel(FIRST_PAGE));
            userDTO.add(linkTo(UserController.class, UserController.class.getMethod("getAllUsers",
                    int.class, int.class), getLastPage(rows, size), size).withRel(LAST_PAGE));
        } catch (NoSuchMethodException e) {
            throw new MethodDoesNotExistException(e.getMessage(), CODE);
        }
    }

    private static int getLastPage(int rows, int size) {
        if ((rows % size) != 0) {
            return (rows / size) + 1;
        } else {
            return (rows / size);
        }
    }
}
