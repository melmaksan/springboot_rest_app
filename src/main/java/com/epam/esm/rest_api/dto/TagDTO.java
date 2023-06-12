package com.epam.esm.rest_api.dto;

import com.epam.esm.certificate_service.entities.Tag;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class TagDTO extends RepresentationModel<TagDTO> {

    private String name;

    public TagDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TagDTO tagDTO = (TagDTO) o;

        return Objects.equals(name, tagDTO.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TagDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
