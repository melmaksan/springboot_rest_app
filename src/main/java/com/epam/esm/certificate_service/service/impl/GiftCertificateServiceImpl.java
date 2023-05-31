package com.epam.esm.certificate_service.service.impl;

import com.epam.esm.certificate_service.dao.GiftCertificateRepository;
import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.exeption_handling.exeptions.EmptyRequestBodyException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.NoSuchDataException;
import com.epam.esm.certificate_service.service.GiftCertificateService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String CODE = "01";

    private final GiftCertificateRepository certificateRepository;

    public GiftCertificateServiceImpl(GiftCertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Override
    public GiftCertificate getGiftCertificateById(long id) {
        GiftCertificate certificate = certificateRepository.findById(id);

        if (certificate != null) {
            return certificate;
        } else {
            throw new NoSuchDataException("There is no certificate with id '" + id + "' in DB", CODE);
        }
    }

    @Override
    public GiftCertificate getGiftCertificateByName(String name) {
        Optional<GiftCertificate> certificateOptional = certificateRepository.findByName(name);

        if (certificateOptional.isPresent()) {
            return certificateOptional.get();
        } else {
            throw new NoSuchDataException("There is no certificate with name '" + name + "' in DB", CODE);
        }
    }


    @Override
    public void addGiftCertificate(GiftCertificate giftCertificate) {
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        if (giftCertificate.getName() == null || giftCertificate.getDescription() == null ||
                giftCertificate.getCreateDate() == null) {
            throw new EmptyRequestBodyException("Fields name, description and createDate " +
                    "are required, please try again!", CODE);
        }
        certificateRepository.saveOrUpdate(giftCertificate);
    }

    @Override
    public void updateGiftCertificate(GiftCertificate giftCertificate) {
        GiftCertificate certificate = certificateRepository.findById(giftCertificate.getId());

        if (certificate != null) {
            giftCertificate.setLastUpdateDate(LocalDateTime.now());
            certificateRepository.saveOrUpdate(giftCertificate);
        } else {
            throw new NoSuchDataException("Can't update certificate with id '" + giftCertificate.getId() +
                    "' because it doesn't exist in DB", CODE);
        }
    }

    @Override
    public void deleteGiftCertificate(long id) {
        GiftCertificate certificate = certificateRepository.findById(id);

        if (certificate != null) {
            certificateRepository.deleteCertificate(id);
        } else {
            throw new NoSuchDataException("Can't delete certificate with id '" + id +
                    "' because it doesn't exist in DB", CODE);
        }
    }

    @Override
    public List<GiftCertificate> getAllGiftCertificates() {
        return certificateRepository.findAll();
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesByPart(String part) {
        return certificateRepository.findByPart(part);
    }

    @Override
    public List<GiftCertificate> sortGiftCertificatesByDateAsc() {
        return certificateRepository.ascByDate();
    }

    @Override
    public List<GiftCertificate> sortGiftCertificatesByDateDesc() {
        return certificateRepository.descByDate();
    }

    @Override
    public List<GiftCertificate> sortGiftCertificatesByNameAsc() {
        return certificateRepository.ascByName();
    }

    @Override
    public List<GiftCertificate> sortGiftCertificatesByNameDesc() {
        return certificateRepository.descByName();
    }

}
