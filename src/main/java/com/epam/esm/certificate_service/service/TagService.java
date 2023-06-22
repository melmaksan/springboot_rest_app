package com.epam.esm.certificate_service.service;

import com.epam.esm.certificate_service.entities.Tag;

import java.util.List;

public interface TagService {

    Tag findById(int id);

    Tag findByName(String name);

    List<Tag> getAllTags(int pageSize, int offset);

    void addTag(Tag tag);

    void deleteTag(int id);

    Tag getWidelyUsedTag(long id);

    int getNumberOfRows();
}
