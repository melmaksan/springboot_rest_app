package com.epam.esm.certificate_service.service;

import com.epam.esm.certificate_service.entities.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TagServiceImplTest {

    @Autowired
    private TagService tagService;

    private static Tag tag1;
    private static Tag tag2;
    private static Tag tag3;

    @BeforeAll
    static void setUp() {
        tag1 = new Tag("discount");
        tag1.setId(1);

        tag2 = new Tag("gift");
        tag2.setId(2);

        tag3 = new Tag("promo_code");
        tag3.setId(3);
    }

    @Test
    void findById() {
        Tag foundTag = tagService.findById(tag1.getId());

        assertNotNull(foundTag);
        assertEquals(tag1, foundTag);
    }

    @Test
    void findByName() {
        Tag foundTag = tagService.findByName(tag2.getName());

        assertNotNull(foundTag);
        assertEquals(tag2, foundTag);
    }

    @Test
    void getAllTags() {
        List<Tag> tags = getTagList();
        List<Tag> foundList = tagService.getAllTags(3, 0);

        assertNotEquals(true, foundList.isEmpty());
        assertEquals(tags, foundList);
    }

    private List<Tag> getTagList() {
        List<Tag> tags = new ArrayList<>();
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);
        return tags;
    }

    @Test
    void addTag() {
        Tag tag4 = new Tag("test");

        tagService.addTag(tag4);
        Tag expectedTag = tagService.findByName(tag4.getName());

        assertNotNull(expectedTag);
        assertEquals(tag4.getName(), expectedTag.getName());
    }

    @Test
    void deleteTag() {
        tagService.deleteTag(3);

        List<Tag> tagList = tagService.getAllTags(3, 0);

        assertNotEquals(true, tagList.isEmpty());
        assertEquals(3, tagList.size());
    }

    @Test
    void getWidelyUsedTag() {
        Tag tag = tagService.getWidelyUsedTag(1L);

        assertNotNull(tag);
        assertEquals("gift", tag.getName());
    }
}