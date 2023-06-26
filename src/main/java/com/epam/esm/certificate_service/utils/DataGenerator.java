package com.epam.esm.certificate_service.utils;

import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.entities.User;
import com.epam.esm.certificate_service.service.GiftCertificateService;
import com.epam.esm.certificate_service.service.TagService;
import com.epam.esm.certificate_service.service.UserService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataGenerator {

    private final TagService tagService;
    private final UserService userService;
    private final GiftCertificateService certificateService;

    private int tagIndex = 1;

    public DataGenerator(TagService tagService, UserService userService, GiftCertificateService certificateService) {
        this.tagService = tagService;
        this.userService = userService;
        this.certificateService = certificateService;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void onApplicationEvent() {
        try {
            Path path = Paths.get("src/main/resources/dictionary.txt");
            List<String> words = Files.readAllLines(path, StandardCharsets.UTF_8);

            List<String> tags = words.subList(1000, 2500);
            List<String> users = words.subList(2500, 5000);
            List<String> certificates = words.subList(5000, 25000);

            generateTags(tags);
            generateUsers(users);
            generateCertificates(tags, certificates);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateTags(List<String> tags) {
        for (int i = 0; i < 1000; i++) {
            tagService.addTag(new Tag(tags.get(i)));
        }
    }

    private void generateUsers(List<String> users) {
        for (int i = 0; i < 1000; i++) {
            userService.addUser(new User(users.get(i), users.get(i + 1000), users.get(i) + "@org.com"));
        }
    }

    private void generateCertificates(List<String> tags, List<String> certificates) {
        for (int i = 0; i < 10000; i++) {
            GiftCertificate certificate = createCertificate(tags, certificates, i);
            certificateService.addGiftCertificate(certificate);
        }
    }

    private GiftCertificate createCertificate(List<String> tagsName, List<String> certificates, int index) {
        String name = certificates.get(index);
        String description = certificates.get(index + 10000);
        int price = 1 + (int) (Math.random() * 1000);
        int duration = 1 + (int) (Math.random() * 100);
        List<Tag> tagList = createTagList(tagsName);

        return new GiftCertificate(name, description, price, duration, tagList);
    }

    private List<Tag> createTagList(List<String> tagsName) {
        int capacity = 1 + (int) (Math.random() * 3);
        List<Tag> tagList = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            if ((tagIndex % 1500) == 0) {
                tagIndex = 0;
            }
            tagList.add(new Tag(tagsName.get(tagIndex)));
            tagIndex++;
        }
        return tagList;
    }
}
