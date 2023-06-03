package com.epam.esm.certificate_service.service;

import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.Tag;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificate getGiftCertificateById(long id);

    GiftCertificate getGiftCertificateByName(String name);

    List<GiftCertificate> getAllGiftCertificates();

    void addGiftCertificate(GiftCertificate certificate);

    void updateGiftCertificate(GiftCertificate certificate);

    void deleteGiftCertificate(long id);

    List<GiftCertificate> getGiftCertificatesByPart(String part);

    List<GiftCertificate> sortGiftCertificatesByDateAsc();

    List<GiftCertificate> sortGiftCertificatesByDateDesc();

    List<GiftCertificate> sortGiftCertificatesByNameAsc();

    List<GiftCertificate> sortGiftCertificatesByNameDesc();

    List<GiftCertificate> getCertificatesByTags(Tag[] tags);
}
