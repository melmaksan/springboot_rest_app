package com.epam.esm.certificate_service.service;

import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.exeption_handling.exeptions.EmptyRequestBodyException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.InvalidRequestParamException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.NoSuchDataException;

import java.util.List;

public interface GiftCertificateService {

    /**
     * This method retrieves certificate from database identified by ID
     * or throws exception
     *
     * @param id identifier of certificate
     * @return certificate, if it exist in DB
     * @throws NoSuchDataException if certificate with such ID doesn't exist
     */
    GiftCertificate getGiftCertificateById(long id);

    /**
     * This method retrieves certificate from database identified by name
     * or throws exception
     *
     * @param name identifier of certificate
     * @return certificate, if it exist in DB
     * @throws NoSuchDataException if certificate with such name doesn't exist
     */
    GiftCertificate getGiftCertificateByName(String name);

    /**
     * Retrieves certificates from database in the given range or throws exception
     *
     * @param pageSize data limit
     * @param offset   data offset
     * @return list of certificates
     * @throws InvalidRequestParamException if page size or page number
     *                                      are negative or equal to zero
     */
    List<GiftCertificate> getAllGiftCertificates(int pageSize, int offset);

    /**
     * Inserts certificate into DB or throws exception
     *
     * @param certificate entity to inserting
     * @throws EmptyRequestBodyException if name, description or createDate field is empty
     */
    void addGiftCertificate(GiftCertificate certificate);

    /**
     * Updates certificate into DB or throws exception
     *
     * @param certificate entity to updating
     * @throws NoSuchDataException if certificate with such ID doesn't exist
     */
    void updateGiftCertificate(GiftCertificate certificate);

    /**
     * Deletes certain certificate identified by ID or throws exception
     *
     * @param id identifier of certificate
     * @throws NoSuchDataException if certificate with such ID doesn't exist
     */
    void deleteGiftCertificate(long id);

    /**
     * Retrieves certificates from the DB that have specified part in the name
     * or in the description column
     *
     * @param part character set
     * @return list of certificates
     */
    List<GiftCertificate> getGiftCertificatesByPart(String part);

    /**
     * Retrieves certificates from database in the given range with
     * ascending sort by date or throws exception
     *
     * @param pageSize data limit
     * @param offset   data offset
     * @return list of certificates
     * @throws InvalidRequestParamException if page size or page number
     *                                      are negative or equal to zero
     */
    List<GiftCertificate> sortGiftCertificatesByDateAsc(int pageSize, int offset);

    /**
     * Retrieves certificates from database in the given range with
     * descending sort by date or throws exception
     *
     * @param pageSize data limit
     * @param offset   data offset
     * @return list of certificates
     * @throws InvalidRequestParamException if page size or page number
     *                                      are negative or equal to zero
     */
    List<GiftCertificate> sortGiftCertificatesByDateDesc(int pageSize, int offset);

    /**
     * Retrieves certificates from database in the given range with
     * ascending sort by name or throws exception
     *
     * @param pageSize data limit
     * @param offset   data offset
     * @return list of certificates
     * @throws InvalidRequestParamException if page size or page number
     *                                      are negative or equal to zero
     */
    List<GiftCertificate> sortGiftCertificatesByNameAsc(int pageSize, int offset);

    /**
     * Retrieves certificates from database in the given range with
     * descending sort by name or throws exception
     *
     * @param pageSize data limit
     * @param offset   data offset
     * @return list of certificates
     * @throws InvalidRequestParamException if page size or page number
     *                                      are negative or equal to zero
     */
    List<GiftCertificate> sortGiftCertificatesByNameDesc(int pageSize, int offset);

    /**
     * This method searches the DB for certificates using the specified tags and return it
     *
     * @param tags array of tags
     * @return list of certificates
     * @throws NoSuchDataException if tag with such name doesn't exist
     */
    List<GiftCertificate> getCertificatesByTags(Tag[] tags);

    /**
     * Method returns number of rows from gift_certificate table in DB
     *
     * @return count of certificates in DB
     */
    int getNumberOfRows();
}
