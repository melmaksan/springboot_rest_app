package com.epam.esm.certificate_service.dao;

import com.epam.esm.certificate_service.entities.Tag;

import java.util.List;

public interface TagRepository {

    Tag findByName(String name);

    Integer getWidelyUsedTag(long id);

    Tag findById(int id);

    List<Tag> findAll(int pageSize, int offset);

    void save(Tag tag);

    void deleteById(int id);
}
