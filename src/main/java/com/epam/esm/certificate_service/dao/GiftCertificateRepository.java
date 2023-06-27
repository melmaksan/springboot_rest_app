package com.epam.esm.certificate_service.dao;

import com.epam.esm.certificate_service.entities.GiftCertificate;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {

    /**
     * Retrieves certificates from database in the given range
     *
     * @param pageSize data limit
     * @param offset   data offset
     * @return list of certificates
     */
    List<GiftCertificate> findAll(int pageSize, int offset);

    /**
     * This method retrieves certificate from database identified by ID
     * or return null
     *
     * @param id identifier of certificate
     * @return certificate, if it exist in DB
     */
    GiftCertificate findById(long id);

    /**
     * This method retrieves certificate from database identified by name
     * or throws exception
     *
     * @param name identifier of certificate
     * @return certificate, if it exist in DB
     * @throws NoResultException if tag with such name doesn't exist
     */
    GiftCertificate findByName(String name);

    /**
     * Inserts or updates certificate into DB depending on the presence of ID
     *
     * @param certificate entity to inserting or updating
     */
    void saveOrUpdate(GiftCertificate certificate);

    /**
     * Deletes certain certificate identified by ID
     *
     * @param id identifier of certificate
     */
    void deleteCertificate(long id);

    /**
     * Retrieves certificates from the DB that have specified
     * part in the name or in the description column
     *
     * @param part character set
     * @return list of certificates
     */
    List<GiftCertificate> findByPart(String part);

    /**
     * Retrieves certificates from database in the given range
     * with ascending sort by date
     *
     * @param pageSize data limit
     * @param offset   data offset
     * @return list of certificates
     */
    List<GiftCertificate> ascByDate(int pageSize, int offset);

    /**
     * Retrieves certificates from database in the given range
     * with descending sort by date
     *
     * @param pageSize data limit
     * @param offset   data offset
     * @return list of certificates
     */
    List<GiftCertificate> descByDate(int pageSize, int offset);

    /**
     * Retrieves certificates from database in the given range
     * with ascending sort by name
     *
     * @param pageSize data limit
     * @param offset   data offset
     * @return list of certificates
     */
    List<GiftCertificate> ascByName(int pageSize, int offset);

    /**
     * Retrieves certificates from database in the given range
     * with descending sort by name
     *
     * @param pageSize data limit
     * @param offset   data offset
     * @return list of certificates
     */
    List<GiftCertificate> descByName(int pageSize, int offset);

    /**
     * Method returns number of rows from gift_certificate table in DB
     *
     * @return count of certificates in DB
     */
    Long getNumberOfRows();
}
