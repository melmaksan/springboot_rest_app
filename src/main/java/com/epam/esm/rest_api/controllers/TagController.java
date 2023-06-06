package com.epam.esm.rest_api.controllers;

import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.service.TagService;
import com.epam.esm.rest_api.dto.Mapper;
import com.epam.esm.rest_api.dto.TagDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class TagController {

    private final TagService tagService;
    private final Mapper mapper;

    public TagController(TagService tagService, Mapper mapper) {
        this.tagService = tagService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/tags/{id}")
    public TagDTO showTag(@PathVariable int id) {
        return mapper.toTagDto(tagService.findById(id));
    }

    @GetMapping(value = "/tags/findByName/{name}")
    public TagDTO getGiftCertificatesByName(@PathVariable String name) {
        return mapper.toTagDto(tagService.findByName(name));
    }

    @GetMapping(value = "/tags")
    public CollectionModel<TagDTO> showTags(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        List<Tag> tagList = tagService.getAllTags(size, (page - 1) * size);

        List<TagDTO> dtoList = tagList.stream().map(mapper::toTagDto).collect(Collectors.toList());
        Link link = linkTo(methodOn(TagController.class).showTags(page, size)).withSelfRel();
        Link deleteTag = linkTo(methodOn(TagController.class).deleteTag(3)).withRel(IanaLinkRelations.parse("delete"));
//        Link deleteTag = linkTo(methodOn(TagController.class).deleteTag(3)).withRel(("delete"));

        return CollectionModel.of(dtoList).add(link, deleteTag);
    }

    @DeleteMapping(value = "/tags/{id}")
    public String deleteTag(@PathVariable int id) {
        tagService.deleteTag(id);
        return "Tag with id = " + id + " was deleted";
    }

    @PostMapping(value = "/tags")
    public TagDTO createTag(@RequestBody Tag tag) {
        tagService.addTag(tag);
        return mapper.toTagDto(tagService.findByName(tag.getName()));
    }


}
