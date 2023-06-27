package com.epam.esm.certificate_service.dao;

import com.epam.esm.certificate_service.entities.User;
import jakarta.persistence.NoResultException;

import java.util.List;

public interface UserRepository {

    /**
     * This method retrieves user from database identified by firstname
     * or throws exception
     *
     * @param name identifier of user
     * @return user, if it exist in DB
     * @throws NoResultException if user with such firstname doesn't exist
     */
    User findByFirstName(String name);

    /**
     * This method retrieves user from database identified by ID
     * or return null
     *
     * @param id identifier of user
     * @return user, if it exist in DB
     */
    User findById(long id);

    /**
     * Retrieves users from database in the given range
     *
     * @param pageSize data limit
     * @param offset   data offset
     * @return list of users
     */
    List<User> findAll(int pageSize, int offset);

    /**
     * Inserts user into DB
     *
     * @param user entity to insert
     */
    void save(User user);

    /**
     * Method returns number of rows from user table in DB
     *
     * @return count of users in DB
     */
    Long getNumberOfRows();
}
