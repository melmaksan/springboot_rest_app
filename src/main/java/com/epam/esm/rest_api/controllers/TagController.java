package com.epam.esm.rest_api.controllers;

import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.service.TagService;
import com.epam.esm.rest_api.dto.Mapper;
import com.epam.esm.rest_api.dto.TagDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<TagDTO> showTags(@RequestParam("page") int page, @RequestParam("size") int size) {
        if (size == 0 || size < 0) {
            size = 5;
        }
        if (page == 0 || page < 0) {
            page = 1;
        }

        return tagService.getAllTags(size, (page-1)*size).stream().map(mapper::toTagDto).collect(Collectors.toList());
    }

    @DeleteMapping(value = "/tags/{id}")
    public String deleteTag(@PathVariable int id) {
        tagService.deleteTag(id);
        return "Tag with id = " + id + " was deleted";
    }

    @PostMapping(value = "/tags")
    public TagDTO createTag(@RequestBody Tag tag) {
        tagService.addTag(tag);
        return mapper.toTagDto(tag);
    }


}
