package com.epam.esm.rest_api.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class UserDTO extends RepresentationModel<UserDTO> {

    private long id;
    private String firstName;
    private String surname;
    private String email;
}
