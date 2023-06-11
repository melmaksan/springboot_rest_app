package com.epam.esm.rest_api.controllers;

import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.service.TagService;
import com.epam.esm.rest_api.controllers.models.TagModelAssembler;
import com.epam.esm.rest_api.dto.Mapper;
import com.epam.esm.rest_api.dto.TagDTO;
import org.springframework.hateoas.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;
    private final Mapper mapper;
    private final TagModelAssembler assembler;

    public TagController(TagService tagService, Mapper mapper, TagModelAssembler assembler) {
        this.tagService = tagService;
        this.mapper = mapper;
        this.assembler = assembler;
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public TagDTO showTag(@PathVariable int id) {
        TagDTO tagDTO = mapper.toTagDto(tagService.findById(id));
        // 1st option
        enrichModelWithLinks(tagDTO);

        return tagDTO;
    }

    @GetMapping(value = "/findByName/{name}")
    public TagDTO getGiftCertificatesByName(@PathVariable String name) {
        return mapper.toTagDto(tagService.findByName(name));
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<TagDTO> showTags(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size) throws NoSuchMethodException {
        List<Tag> tagList = tagService.getAllTags(size, (page - 1) * size);
        // 2nd option
        Link linkToAll = linkTo(methodOn(TagController.class).showTags(page, size)).withSelfRel();
        return assembler.toCollectionModel(tagList).add(linkToAll);
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

    private static void enrichModelWithLinks(TagDTO entity) {
        Link[] links = new Link[3];
        try {
            links[0] = linkTo(methodOn(TagController.class).showTag(entity.getId())).withSelfRel();
            links[1] = linkTo(TagController.class, TagController.class.getMethod("deleteTag", int.class), entity.getId()).withRel("delete");
            links[2] = linkTo(TagController.class, TagController.class.getMethod("showTags", int.class, int.class), 1, 5).withRel("showTags");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
        entity.add(links);
    }




}
