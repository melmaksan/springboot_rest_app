package com.epam.esm.certificate_service.dao;

import com.epam.esm.certificate_service.entities.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {

    List<GiftCertificate> findAll();

    GiftCertificate findById(long id);

    GiftCertificate findByName(String name);

    void saveOrUpdate(GiftCertificate certificate);

    void deleteCertificate(long id);

    List<GiftCertificate> findByPart(String part);

    List<GiftCertificate> ascByDate();

    List<GiftCertificate> descByDate();

    List<GiftCertificate> ascByName();

    List<GiftCertificate> descByName();
}
