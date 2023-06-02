package com.epam.esm.certificate_service.service.impl;

import com.epam.esm.certificate_service.dao.TagRepository;
import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.exeption_handling.exeptions.EmptyRequestBodyException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.NoSuchDataException;
import com.epam.esm.certificate_service.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private static final String CODE = "02";

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag findById(int id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);

        if (optionalTag.isEmpty()) {
            throw new NoSuchDataException("There is no tag with id '" + id + "' in DB", CODE);
        }

        return optionalTag.get();
    }

    @Override
    public Tag findByName(String name) {
        Optional<Tag> optionalTag = tagRepository.findByName(name);

        if (optionalTag.isEmpty()) {
            throw new NoSuchDataException("There is no tag with name '" + name + "' in DB", CODE);
        }

        return optionalTag.get();
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
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
        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (optionalTag.isPresent()) {
            tagRepository.deleteById(optionalTag.get().getId());
        } else {
            throw new NoSuchDataException("Can't delete tag with id '" + id +
                    "' because it doesn't exist in DB", CODE);
        }
    }

}
