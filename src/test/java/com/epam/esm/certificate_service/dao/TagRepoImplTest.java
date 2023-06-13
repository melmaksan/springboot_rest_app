package com.epam.esm.certificate_service.dao;

import com.epam.esm.certificate_service.entities.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TagRepoImplTest {

    @Autowired
    private TagRepository tagRepo;

    @Test
    void findByName() {
        Tag tag = tagRepo.findByName("gift");

        assertNotNull(tag);
        assertEquals(2, tag.getId());
    }

    @Test
    void getWidelyUsedTag() {
        int tagId = tagRepo.getWidelyUsedTag(2L);
        Tag tag = tagRepo.findById(tagId);

        assertNotNull(tag);
        assertEquals("discount", tag.getName());
    }

    @Test
    void findById() {
        Tag tag = tagRepo.findById(3);

        assertNotNull(tag);
        assertEquals("promo_code", tag.getName());
    }

    @Test
    void findAll() {
        List<Tag> tagList = tagRepo.findAll(5, 0);

        assertNotNull(tagList);
        assertEquals(3, tagList.size(), "should be 3 tags in the list");
    }

    @Test
    void save() {
        Tag tag = new Tag("test_tag");
        tagRepo.save(tag);

        List<Tag> tagList = tagRepo.findAll(5, 0);

        assertEquals(4, tagList.size(), "there are 4 tags after insert");
    }

    @Test
    void deleteById() {
        tagRepo.deleteById(2);

        List<Tag> tagList = tagRepo.findAll(5, 0);

        assertEquals(3, tagList.size(), "there are 3 tag after delete");
    }
}