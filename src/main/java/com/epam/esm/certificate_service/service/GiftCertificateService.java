package com.epam.esm.certificate_service.service;

import com.epam.esm.certificate_service.entities.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificate getGiftCertificateById(long id);

    GiftCertificate getGiftCertificateByName(String name);

    List<GiftCertificate> getAllGiftCertificates();

    void addGiftCertificate(GiftCertificate userDetail);

    void updateGiftCertificate(GiftCertificate userDetail);

    void deleteGiftCertificate(long id);

    List<GiftCertificate> getGiftCertificatesByPart(String part);

    List<GiftCertificate> sortGiftCertificatesByDateAsc();

    List<GiftCertificate> sortGiftCertificatesByDateDesc();

    List<GiftCertificate> sortGiftCertificatesByNameAsc();

    List<GiftCertificate> sortGiftCertificatesByNameDesc();

}
