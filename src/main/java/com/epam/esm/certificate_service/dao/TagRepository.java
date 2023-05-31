package com.epam.esm.certificate_service.dao;

import com.epam.esm.certificate_service.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findByName(String name);
}
