package com.epam.esm.rest_api.controllers;

import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.service.TagService;
import com.epam.esm.rest_api.dto.Mapper;
import com.epam.esm.rest_api.dto.TagDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public TagDTO showTag(@PathVariable int id) {
        return mapper.toTagDto(tagService.findById(id));
    }

    @GetMapping(value = "/findByName/{name}")
    public EntityModel<TagDTO> getGiftCertificatesByName(@PathVariable String name) {
        TagDTO tagDTO = mapper.toTagDto(tagService.findByName(name));
        Link link;
        try {
            link = linkTo(TagController.class,
                    TagController.class.getMethod("getGiftCertificatesByName", String.class), name)
                    .withRel("get tag with '" + name + "' name");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

        return EntityModel.of(tagDTO).add(link);
    }

    @GetMapping
    public CollectionModel<TagDTO> showTags(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        List<Tag> tagList = tagService.getAllTags(size, (page - 1) * size);

        List<TagDTO> dtoList = tagList.stream().map(mapper::toTagDto).collect(Collectors.toList());
        Link link = linkTo(methodOn(TagController.class).showTags(page, size)).withSelfRel();

        return CollectionModel.of(dtoList).add(link);
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
