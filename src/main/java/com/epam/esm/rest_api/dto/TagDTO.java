package com.epam.esm.rest_api.dto;

import org.springframework.hateoas.EntityModel;

import java.util.Objects;

public class TagDTO extends EntityModel<TagDTO> {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        if (id != tagDTO.id) return false;
        return Objects.equals(name, tagDTO.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TagDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "} " + super.toString();
    }
}
