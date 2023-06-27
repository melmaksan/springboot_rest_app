package com.epam.esm.certificate_service.dao;

import com.epam.esm.certificate_service.entities.Tag;
import jakarta.persistence.NoResultException;

import java.util.List;

public interface TagRepository {

    /**
     * This method retrieves tag from database identified by name
     * or throws exception
     *
     * @param name identifier of tag
     * @return tag, if it exist in DB
     * @throws NoResultException if tag with such name doesn't exist
     */
    Tag findByName(String name);

    /**
     * This method returns ID of the tag that most often found in the user's orders
     *
     * @param id user ID
     * @return ID of widely used tag
     */
    Integer getWidelyUsedTag(long id);

    /**
     * This method retrieves tag from database identified by ID
     * or return null
     *
     * @param id identifier of tag
     * @return tag, if it exist in DB
     */
    Tag findById(int id);

    /**
     * Retrieves tags from database in the given range
     *
     * @param pageSize data limit
     * @param offset   data offset
     * @return list of tags
     */
    List<Tag> findAll(int pageSize, int offset);

    /**
     * Inserts tag into DB
     *
     * @param tag entity to insert
     */
    void save(Tag tag);

    /**
     * Deletes certain tag identified by ID
     *
     * @param id identifier of tag
     */
    void deleteById(int id);

    /**
     * Method returns number of rows from tag table in DB
     *
     * @return count of tags in DB
     */
    Long getNumberOfRows();
}
