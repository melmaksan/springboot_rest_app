package com.epam.esm.certificate_service.service.impl;

import com.epam.esm.certificate_service.dao.TagRepository;
import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.exeption_handling.exeptions.EmptyRequestBodyException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.NoSuchDataException;
import com.epam.esm.certificate_service.service.TagService;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private static final String CODE = "02";

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag findById(int id) {
        Tag tag = tagRepository.findById(id);

        if (tag != null) {
            return tag;
        } else {
            throw new NoSuchDataException("There is no tag with id '" + id + "' in DB", CODE);
        }
    }

    @Override
    public Tag findByName(String name) {
        try {
            return tagRepository.findByName(name);
        } catch (NoResultException ex) {
            throw new NoSuchDataException("There is no tag with name '" + name + "' in DB", CODE);
        }
    }

    @Override
    public List<Tag> getAllTags(int pageSize, int offset) {
        return tagRepository.findAll(pageSize, offset);
    }

    @Override
    public void addTag(Tag tag) {
        if (tag.getName() == null) {
            throw new EmptyRequestBodyException("Field name is required, please try again!", CODE);
        }
        tagRepository.save(tag);
    }

    @Override
    public void deleteTag(int id) {
        Tag tag = tagRepository.findById(id);

        if (tag != null) {
            tagRepository.deleteById(id);
        } else {
            throw new NoSuchDataException("Can't delete tag with id '" + id +
                    "' because it doesn't exist in DB", CODE);
        }
    }

    @Override
    public Tag getWidelyUsedTag(long id) {
        int tagId = tagRepository.getWidelyUsedTag(id);
        return tagRepository.findById(tagId);
    }

}
