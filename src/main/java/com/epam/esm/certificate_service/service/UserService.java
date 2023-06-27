package com.epam.esm.certificate_service.service;

import com.epam.esm.certificate_service.entities.User;
import com.epam.esm.certificate_service.exeption_handling.exeptions.EmptyRequestBodyException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.InvalidRequestParamException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.NoSuchDataException;

import java.util.List;

public interface UserService {

    /**
     * This method retrieves user from database identified by ID
     * or throws exception
     *
     * @param id identifier of user
     * @return user, if it exist in DB
     * @throws NoSuchDataException if user with such ID doesn't exist
     */
    User findById(long id);

    /**
     * This method retrieves user from database identified by firstname
     * or throws exception
     *
     * @param userName identifier of user
     * @return user, if it exist in DB
     * @throws NoSuchDataException if user with such firstname doesn't exist
     */
    User findByName(String userName);

    /**
     * Retrieves users from database in the given range or throws exception
     *
     * @param pageSize data limit
     * @param offset   data offset
     * @return list of users
     * @throws InvalidRequestParamException if page size or page number
     *                                      are negative or equal to zero
     */
    List<User> getAllUsers(int pageSize, int offset);

    /**
     * This method looks for a certificate by name in DB and adds an
     * order to the user
     *
     * @param user entity who makes the order
     * @param certificateName identifier of certificate
     */
    void buyCertificate(User user, String certificateName);

    /**
     * Method returns number of rows from tag table in DB
     *
     * @return count of tags in DB
     */
    int getNumberOfRows();

    /**
     * Inserts user into DB or throws exception
     *
     * @param user entity to insert
     * @throws EmptyRequestBodyException if firstname, surname or email field is empty
     */
    void addUser(User user);
}
