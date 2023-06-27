package com.epam.esm.rest_api.dto;

import com.epam.esm.certificate_service.entities.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.rest_api.dto.LinkAssembler.*;

@Component
public class Mapper {

    /**
     * Method converts certificate entity to DTO
     *
     * @param certificate entity that will be converted
     * @return certificate DTO
     */
    public CertificateDTO toCertificateDto(GiftCertificate certificate) {
        long id = certificate.getId();
        String name = certificate.getName();
        String description = certificate.getDescription();
        int price = certificate.getPrice();
        int duration = certificate.getDuration();
        LocalDate createDate = certificate.getCreateDate().toLocalDate();
        List<String> tags = new ArrayList<>();
        if (certificate.getTags() != null) {
            tags = certificate.getTags().stream().map(Tag::getName).collect(Collectors.toList());
        }

        CertificateDTO certificateDTO = new CertificateDTO(id, name, description, price, duration, createDate, tags);
        addLinksToCertificateDTO(certificate, certificateDTO);

        return certificateDTO;
    }

    /**
     * Method converts tag entity to DTO
     *
     * @param tag entity that will be converted
     * @return tag DTO
     */
    public TagDTO toTagDto(Tag tag) {
        TagDTO tagDTO = new TagDTO(tag.getId(), tag.getName());
        addLinksToTagDTO(tag, tagDTO);

        return tagDTO;
    }

    /**
     * Method converts user entity to DTO
     *
     * @param user entity that will be converted
     * @return user DTO
     */
    public UserDTO toUserDto(User user) {
        long id = user.getId();
        String name = user.getFirstName();
        String surname = user.getSurname();
        String email = user.getEmail();

        UserDTO userDTO = new UserDTO(id, name, surname, email);
        addLinksToUserDTO(user, userDTO);

        return userDTO;
    }

    /**
     * Method converts order entity to DTO
     *
     * @param order entity that will be converted
     * @return order DTO
     */
    public OrderDTO toOrderDto(Order order) {
        long id = order.getId();
        int price = order.getPrice();
        LocalDateTime dateTime = order.getTime();
        CertificateDTO certificateDTO = toCertificateDto(order.getCertificate());

        return new OrderDTO(id, price, dateTime, certificateDTO);
    }
}
