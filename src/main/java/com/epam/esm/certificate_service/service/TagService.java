package com.epam.esm.certificate_service.service;

import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.exeption_handling.exeptions.EmptyRequestBodyException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.InvalidRequestParamException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.NoSuchDataException;
import jakarta.persistence.NoResultException;

import java.util.List;

public interface TagService {

    /**
     * This method retrieves tag from database identified by ID
     * or throws exception
     *
     * @param id identifier of tag
     * @return tag, if it exist in DB
     * @throws NoSuchDataException if tag with such ID doesn't exist
     */
    Tag findById(int id);

    /**
     * This method retrieves tag from database identified by name
     * or throws exception
     *
     * @param name identifier of tag
     * @return tag, if it exist in DB
     * @throws NoSuchDataException if tag with such name doesn't exist
     */
    Tag findByName(String name);

    /**
     * Retrieves tags from database in the given range or throws exception
     *
     * @param pageSize data limit
     * @param offset   data offset
     * @return list of tags
     * @throws InvalidRequestParamException if page size or page number
     *                                      are negative or equal to zero
     */
    List<Tag> getAllTags(int pageSize, int offset);

    /**
     * Inserts tag into DB or throws exception
     *
     * @param tag entity to insert
     * @throws EmptyRequestBodyException if name field is empty
     */
    void addTag(Tag tag);

    /**
     * Deletes certain tag identified by ID or throws exception
     *
     * @param id identifier of tag
     * @throws NoSuchDataException if tag with such ID doesn't exist
     */
    void deleteTag(int id);

    /**
     * This method returns tag that most often found in the user's orders
     *
     * @param id user ID
     * @return widely used tag
     */
    Tag getWidelyUsedTag(long id);

    /**
     * Method returns number of rows from tag table in DB
     *
     * @return count of tags in DB
     */
    int getNumberOfRows();
}
