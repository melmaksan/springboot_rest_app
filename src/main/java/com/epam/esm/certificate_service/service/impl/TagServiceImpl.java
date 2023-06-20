package com.epam.esm.certificate_service.service.impl;

import com.epam.esm.certificate_service.dao.TagRepository;
import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.exeption_handling.exeptions.EmptyRequestBodyException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.InvalidRequestParamException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.NoSuchDataException;
import com.epam.esm.certificate_service.service.TagService;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private static final String CODE = "02";

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional(isolation=Isolation.REPEATABLE_READ)
    public Tag findById(int id) {
        Tag tag = tagRepository.findById(id);

        if (tag != null) {
            return tag;
        } else {
            throw new NoSuchDataException("There is no tag with id '" + id + "' in DB", CODE);
        }
    }

    @Override
    @Transactional(isolation=Isolation.REPEATABLE_READ)
    public Tag findByName(String name) {
        try {
            return tagRepository.findByName(name);
        } catch (NoResultException ex) {
            throw new NoSuchDataException("There is no tag with name '" + name + "' in DB", CODE);
        }
    }

    @Override
    @Transactional(isolation=Isolation.REPEATABLE_READ)
    public List<Tag> getAllTags(int pageSize, int offset) {
        if (pageSize > 0 && offset >= 0) {
            return tagRepository.findAll(pageSize, offset);
        } else {
            throw new InvalidRequestParamException("Page number or size can't be 0 or negative", CODE);
        }
    }

    @Override
    @Transactional(isolation= Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW,
    rollbackFor = EmptyRequestBodyException.class)
    public void addTag(Tag tag) {
        if (tag.getName() == null) {
            throw new EmptyRequestBodyException("Field name is required, please try again!", CODE);
        }
        tagRepository.save(tag);
    }

    @Override
    @Transactional(isolation=Isolation.SERIALIZABLE, rollbackFor = NoSuchDataException.class)
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
    @Transactional(isolation=Isolation.REPEATABLE_READ)
    public Tag getWidelyUsedTag(long id) {
        int tagId = tagRepository.getWidelyUsedTag(id);
        return tagRepository.findById(tagId);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public int getNumberOfRows() {
        return Math.toIntExact(tagRepository.getNumberOfRows());
    }

}
