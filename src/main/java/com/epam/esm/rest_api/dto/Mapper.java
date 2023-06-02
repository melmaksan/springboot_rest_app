package com.epam.esm.rest_api.dto;

import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            tags = certificate.getTags()
                    .stream()
                    .map(Tag::getName)
                    .collect(Collectors.toList());
        }

        return new CertificateDTO(name, description, price, duration, createDate, tags);
    }

    public TagDTO toTagDto(Tag tag) {
        String name = tag.getName();

        return new TagDTO(name);
    }

    public UserDTO toUserDto(User user) {
        String name = user.getFirstName();
        String surname = user.getSurname();
        String email = user.getEmail();

        return new UserDTO(name, surname, email);
    }
}
