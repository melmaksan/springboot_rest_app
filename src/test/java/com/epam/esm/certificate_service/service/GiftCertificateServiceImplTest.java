package com.epam.esm.certificate_service.service;

import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GiftCertificateServiceImplTest {

    @Autowired
    private GiftCertificateService certificateService;

    private static GiftCertificate certificate1;

    @BeforeAll
    static void setUp() {
        certificate1 = new GiftCertificate();

        certificate1.setId(1L);
        certificate1.setName("Promo_1000");
        certificate1.setDescription("gift certificate gives a discount of 1000UAH for 30 days");
        certificate1.setPrice(1000);
        certificate1.setDuration(30);
        certificate1.setCreateDate(LocalDateTime.of(2023, 6, 14, 12, 0));
        certificate1.setLastUpdateDate(LocalDateTime.of(2023, 6, 14, 12, 0));
    }

    @Test
    void getGiftCertificateById() {
        GiftCertificate foundCertificate = certificateService.getGiftCertificateById(certificate1.getId());

        assertNotNull(foundCertificate);
        assertEquals(certificate1, foundCertificate);
    }

    @Test
    void getGiftCertificateByName() {
        GiftCertificate foundCertificate = certificateService.getGiftCertificateByName(certificate1.getName());

        assertNotNull(foundCertificate);
        assertEquals(certificate1, foundCertificate);
    }

    @Test
    void addGiftCertificate() {
        GiftCertificate certificate2 = getTestCertificate();

        certificateService.addGiftCertificate(certificate2);
        GiftCertificate expectedCertificate = certificateService.getGiftCertificateByName(certificate2.getName());

        assertNotNull(expectedCertificate);
        assertEquals(certificate2.getDuration(), expectedCertificate.getDuration());

    }

    private GiftCertificate getTestCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("test_certificate");
        giftCertificate.setDescription("testing");
        giftCertificate.setPrice(100);
        giftCertificate.setDuration(10);
        giftCertificate.setCreateDate(LocalDateTime.now());
        return giftCertificate;
    }

    @Test
    void updateGiftCertificate() {
        GiftCertificate certificate3 = getTestCertificate();
        certificate3.setId(6);
        certificate3.setDescription("update");
        certificate3.setDuration(777);

        certificateService.updateGiftCertificate(certificate3);
        GiftCertificate expectedCertificate = certificateService.getGiftCertificateById(6);

        assertNotNull(expectedCertificate);
        assertEquals(777, expectedCertificate.getDuration());
        assertEquals("update", expectedCertificate.getDescription());

    }

    @Test
    void deleteGiftCertificate() {
        certificateService.deleteGiftCertificate(5);

        List<GiftCertificate> certificates = certificateService.getAllGiftCertificates(6, 0);

        assertNotEquals(true, certificates.isEmpty());
        assertEquals(6, certificates.size());
    }

    @Test
    void getAllGiftCertificates() {
        List<GiftCertificate> foundList = certificateService.getAllGiftCertificates(5, 0);

        assertNotEquals(true, foundList.isEmpty());
        assertEquals(5, foundList.size());
        assertEquals(certificate1, foundList.get(0));
    }

    @Test
    void getGiftCertificatesByPart() {
        List<GiftCertificate> certificateList = certificateService.getGiftCertificatesByPart("00");

        assertNotEquals(true, certificateList.isEmpty());
        assertEquals(4, certificateList.size(), "should be 4 certificates");
    }

    @Test
    void sortGiftCertificatesByDateAsc() {
        List<GiftCertificate> certificates = certificateService.sortGiftCertificatesByDateAsc(5, 0);

        assertNotEquals(true, certificates.isEmpty());
        assertEquals("Promo_1000", certificates.get(0).getName());
        assertEquals(5, certificates.size(), "should be 5 certificates");
    }

    @Test
    void sortGiftCertificatesByDateDesc() {
        List<GiftCertificate> certificates = certificateService.sortGiftCertificatesByDateDesc(5, 0);

        assertNotEquals(true, certificates.isEmpty());
        assertEquals("MELMAN50", certificates.get(0).getName());
        assertEquals(5, certificates.size(), "should be 5 certificates");
    }

    @Test
    void sortGiftCertificatesByNameAsc() {
        List<GiftCertificate> certificates = certificateService.sortGiftCertificatesByNameAsc(4, 1);

        assertNotEquals(true, certificates.isEmpty());
        assertEquals("MELMAN50", certificates.get(0).getName());
        assertEquals(4, certificates.size(), "should be 4 certificates");
    }

    @Test
    void sortGiftCertificatesByNameDesc() {
        List<GiftCertificate> certificates = certificateService.sortGiftCertificatesByNameDesc(4, 0);

        assertNotEquals(true, certificates.isEmpty());
        assertEquals("Promo_500", certificates.get(0).getName());
        assertEquals(4, certificates.size(), "should be 4 certificates");
    }

    @Test
    void getCertificatesByTags() {
        Tag[] tags = new Tag[]{new Tag("discount"), new Tag("promo_code")};

        List<GiftCertificate> certificates = certificateService.getCertificatesByTags(tags);

        assertNotEquals(true, certificates.isEmpty());
        assertEquals(4, certificates.size(), "should be 4 certificates");
    }
}