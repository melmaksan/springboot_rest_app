package com.epam.esm.certificate_service.dao;

import com.epam.esm.certificate_service.entities.User;

import java.util.List;

public interface UserRepository {

    User findByFirstName(String name);

    User findById(long id);

    List<User> findAll(int pageSize, int offset);

    void save(User user);

}
