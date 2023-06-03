package com.epam.esm.certificate_service.dao;

import com.epam.esm.certificate_service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByFirstName(String name);
}
