package com.epam.esm.rest_api.controllers.models;

import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.rest_api.controllers.TagController;
import com.epam.esm.rest_api.dto.Mapper;
import com.epam.esm.rest_api.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelAssembler extends RepresentationModelAssemblerSupport<Tag, TagDTO> {

    @Autowired
    private Mapper mapper;

    public TagModelAssembler() {
        super(TagController.class, TagDTO.class);
    }

    @Override
    public TagDTO toModel(Tag entity) {
        Link[] links = new Link[3];
        try {
            links[0] = linkTo(methodOn(TagController.class).showTag(entity.getId())).withSelfRel();
            links[1] = linkTo(TagController.class, TagController.class.getMethod("deleteTag", int.class), entity.getId()).withRel("delete");
            links[2] = linkTo(TagController.class, TagController.class.getMethod("showTags", int.class, int.class), 1, 5).withRel("showTags");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
        TagDTO res = mapper.toTagDto(entity);
        res.add(links);

        return res;
    }
}
