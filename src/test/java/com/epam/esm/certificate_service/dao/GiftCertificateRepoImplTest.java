package com.epam.esm.certificate_service.dao;

import com.epam.esm.certificate_service.entities.GiftCertificate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GiftCertificateRepoImplTest {

    @Autowired
    GiftCertificateRepository giftCertificateRepo;

    private static GiftCertificate certificate;

    @BeforeAll
    static void setUp() {
        certificate = new GiftCertificate();

        certificate.setName("Name");
        certificate.setDescription("Test");
        certificate.setPrice(77);
        certificate.setDuration(13);
        certificate.setCreateDate(LocalDateTime.of(2023, 6, 14, 12, 0, 4));
        certificate.setLastUpdateDate(LocalDateTime.of(2023, 6, 14, 12, 0, 4));
    }

    @Test
    void findById() {
        GiftCertificate certificate = giftCertificateRepo.findById(3);

        assertNotNull(certificate);
        assertEquals("Promo_500", certificate.getName());
    }

    @Test
    void findByName() {
        GiftCertificate certificate = giftCertificateRepo.findByName("MELMAN50");

        assertNotNull(certificate);
        assertEquals(5, certificate.getId());
    }

    @Test
    void findAll() {
        List<GiftCertificate> certificateList = giftCertificateRepo.findAll(5, 0);

        assertNotEquals(true, certificateList.isEmpty());
        assertEquals(5, certificateList.size(), "should be 5 default certificates");
    }

    @Test
    void saveOrUpdate() {
        giftCertificateRepo.saveOrUpdate(certificate);

        List<GiftCertificate> certificateList = giftCertificateRepo.findAll(10, 0);

        assertNotEquals(true, certificateList.isEmpty());
        assertEquals(6, certificateList.size(), "should be 6 default certificates");

        GiftCertificate existCertificate = giftCertificateRepo.findByName(certificate.getName());
        existCertificate.setDescription("update");
        existCertificate.setDuration(130);
        existCertificate.setLastUpdateDate(LocalDateTime.now());

        long id = existCertificate.getId();
        giftCertificateRepo.saveOrUpdate(existCertificate);

        GiftCertificate updatedCertificate = giftCertificateRepo.findById(id);

        assertNotNull(updatedCertificate);
        assertEquals("Name", updatedCertificate.getName());
        assertEquals("update", updatedCertificate.getDescription());
        assertEquals(77, updatedCertificate.getPrice());
        assertEquals(130, updatedCertificate.getDuration());
    }

    @Test
    void findByPart() {
        List<GiftCertificate> certificateList = giftCertificateRepo.findByPart("50");

        assertNotEquals(true, certificateList.isEmpty());
        assertEquals(3, certificateList.size(), "should be 3 certificates");
    }

    @Test
    void ascByDate() {
        List<GiftCertificate> certificates = giftCertificateRepo.ascByDate(5, 1);

        assertNotEquals(true, certificates.isEmpty());
        assertEquals("Promo_100", certificates.get(0).getName());
        assertEquals(4, certificates.size(), "should be 4 certificates");
    }

    @Test
    void descByDate() {
        List<GiftCertificate> certificates = giftCertificateRepo.ascByName(4, 0);

        assertNotEquals(true, certificates.isEmpty());
        assertEquals("MELMAN1500", certificates.get(0).getName());
        assertEquals(4, certificates.size(), "should be 4 certificates");
    }

    @Test
    void ascByName() {
        List<GiftCertificate> certificates = giftCertificateRepo.ascByName(4, 1);

        assertNotEquals(true, certificates.isEmpty());
        assertEquals("MELMAN50", certificates.get(0).getName());
        assertEquals(4, certificates.size(), "should be 4 certificates");
    }

    @Test
    void descByName() {
        List<GiftCertificate> certificates = giftCertificateRepo.descByName(3, 0);

        assertNotEquals(true, certificates.isEmpty());
        assertEquals("Promo_500", certificates.get(0).getName());
        assertEquals(3, certificates.size(), "should be 3 certificates");
    }

    @Test
    void deleteCertificate() {
        giftCertificateRepo.deleteCertificate(4);

        List<GiftCertificate> certificateList = giftCertificateRepo.findAll(5, 0);

        assertNotEquals(true, certificateList.isEmpty());
        assertEquals(5, certificateList.size(), "there are 5 certificates after delete");
    }
}