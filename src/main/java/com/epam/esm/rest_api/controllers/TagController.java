package com.epam.esm.rest_api.controllers;

import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.service.TagService;
import com.epam.esm.rest_api.dto.Mapper;
import com.epam.esm.rest_api.dto.TagDTO;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.rest_api.Constants.*;

@RestController
@RequestMapping(value = "/api/tags", produces = MediaTypes.HAL_JSON_VALUE)
public class TagController {

    private final TagService tagService;
    private final Mapper mapper;

    public TagController(TagService tagService, Mapper mapper) {
        this.tagService = tagService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/{id}")
    public TagDTO getTagById(@PathVariable int id) {
        return mapper.toTagDto(tagService.findById(id));
    }

    @GetMapping(value = "/findByName/{name}")
    public TagDTO getTagByName(@PathVariable String name) {
        return mapper.toTagDto(tagService.findByName(name));
    }

    @GetMapping
    public List<TagDTO> getAllTags(
            @RequestParam(value = PAGE, required = false, defaultValue = DEFAULT_TAG_PAGE_NUMBER) int page,
            @RequestParam(value = SIZE, required = false, defaultValue = DEFAULT_TAG_PAGE_SIZE) int size) {
        return tagService.getAllTags(size, (page - 1) * size)
                .stream().map(mapper::toTagDto).collect(Collectors.toList());
    }

    @DeleteMapping(value = "/{id}")
    public String deleteTag(@PathVariable int id) {
        tagService.deleteTag(id);
        return "Tag with id = " + id + " was deleted";
    }

    @PostMapping
    public TagDTO createTag(@RequestBody Tag tag) {
        tagService.addTag(tag);
        return mapper.toTagDto(tagService.findByName(tag.getName()));
    }
}
