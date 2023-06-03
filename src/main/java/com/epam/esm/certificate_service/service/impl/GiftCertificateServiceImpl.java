package com.epam.esm.certificate_service.service.impl;

import com.epam.esm.certificate_service.dao.GiftCertificateRepository;
import com.epam.esm.certificate_service.dao.TagRepository;
import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.exeption_handling.exeptions.EmptyRequestBodyException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.NoSuchDataException;
import com.epam.esm.certificate_service.service.GiftCertificateService;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String CODE = "01";

    private final GiftCertificateRepository certificateRepository;
    private final TagRepository tagRepository;

    public GiftCertificateServiceImpl(GiftCertificateRepository certificateRepository, TagRepository tagRepository) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
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
        try {
            return certificateRepository.findByName(name);
        } catch (NoResultException ex){
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

        if (giftCertificate.getTags() != null) {
            List<Tag> tags = checkTags(giftCertificate);
            giftCertificate.setTags(tags);
        }

        certificateRepository.saveOrUpdate(giftCertificate);
    }

    @Override
    public void updateGiftCertificate(GiftCertificate updateCertificate) {
        GiftCertificate certificate = certificateRepository.findById(updateCertificate.getId());

        if (certificate != null) {
            fieldsUpdate(certificate, updateCertificate);
            List<Tag> tags = checkTags(certificate);
            certificate.setTags(tags);
            certificateRepository.saveOrUpdate(certificate);
        } else {
            throw new NoSuchDataException("Can't update certificate with id '" + updateCertificate.getId() +
                    "' because it doesn't exist in DB", CODE);
        }
    }

    private List<Tag> checkTags(GiftCertificate certificate) {
        List<Tag> tagList = certificate.getTags();
        List<Tag> checkedTags = new ArrayList<>();
        for (Tag tag : tagList) {
            try {
                Tag existTag = tagRepository.findByName(tag.getName());
                checkedTags.add(existTag);
            } catch (NoResultException ex) {
                checkedTags.add(tag);
            }
        }
        return checkedTags;
    }

    private void fieldsUpdate(GiftCertificate existCertificate, GiftCertificate updateCertificate) {
        if (updateCertificate.getName() != null) {
            existCertificate.setName(updateCertificate.getName());
        }
        if (updateCertificate.getDescription() != null) {
            existCertificate.setDescription(updateCertificate.getDescription());
        }
        if (updateCertificate.getPrice() != 0) {
            existCertificate.setPrice(updateCertificate.getPrice());
        }
        if (updateCertificate.getDuration() != 0) {
            existCertificate.setDuration(updateCertificate.getDuration());
        }
        if (updateCertificate.getTags() != null) {
            List<Tag> tags = existCertificate.getTags();
            tags.addAll(updateCertificate.getTags());
            existCertificate.setTags(tags);
        }
        existCertificate.setLastUpdateDate(LocalDateTime.now());
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

    @Override
    public List<GiftCertificate> getCertificatesByTags(Tag[] tags) {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        for (Tag tag : tags) {
            try {
                Tag existTag = tagRepository.findByName(tag.getName());
                giftCertificates.addAll(existTag.getCertificates());
            } catch (NoResultException ex) {
                ex.printStackTrace();
            }
        }
        return giftCertificates.stream().distinct().collect(Collectors.toList());
    }

}
