package com.epam.esm.rest_api.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TagDTO extends RepresentationModel<TagDTO> {

    private int id;
    private String name;
}
