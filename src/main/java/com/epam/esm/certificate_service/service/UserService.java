package com.epam.esm.certificate_service.service;

import com.epam.esm.certificate_service.entities.User;

import java.util.List;

public interface UserService {

    User findById(long id);

    User findByName(String userName);

    List<User> getAllUsers(int pageSize, int offset);

    void buyCertificate(User user, String certificateName);

    int getNumberOfRows();

    void addUser(User user);
}
