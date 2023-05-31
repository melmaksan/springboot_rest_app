package com.epam.esm.certificate_service.dao.impl;

import com.epam.esm.certificate_service.dao.GiftCertificateRepository;
import com.epam.esm.certificate_service.entities.GiftCertificate;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateRepository {

    private final EntityManager entityManager;

    public GiftCertificateDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public GiftCertificate findById(long id) {
        return entityManager.find(GiftCertificate.class, id);
    }

    @Override
    public Optional<GiftCertificate> findByName(String paramName) {
        return entityManager.createQuery("from GiftCertificate where name = " + paramName).getResultList().stream().findAny();
    }

    @Override
    public List<GiftCertificate> findAll() {
        return (List<GiftCertificate>) entityManager.createQuery("from GiftCertificate").getResultList();
    }

    @Override
    @Transactional
    public void saveOrUpdate(GiftCertificate certificate) {
        entityManager.merge(certificate);
    }

    @Override
    public List<GiftCertificate> findByPart(String part) {
        return (List<GiftCertificate>) entityManager.createQuery
                ("from GiftCertificate WHERE name LIKE %" + part + "% OR description LIKE %" + part + "%").getResultList();
    }

    @Override
    public List<GiftCertificate> ascByDate() {
        return (List<GiftCertificate>) entityManager.createQuery("from GiftCertificate ORDER BY createDate ASC").getResultList();
    }

    @Override
    public List<GiftCertificate> descByDate() {
        return (List<GiftCertificate>) entityManager.createQuery("from GiftCertificate ORDER BY createDate DESC").getResultList();
    }

    @Override
    public List<GiftCertificate> ascByName() {
        return (List<GiftCertificate>) entityManager.createQuery("from GiftCertificate ORDER BY name ASC").getResultList();
    }

    @Override
    public List<GiftCertificate> descByName() {
        return (List<GiftCertificate>) entityManager.createQuery("from GiftCertificate ORDER BY name DESC").getResultList();
    }

    @Override
    public void deleteCertificate(long id) {
        entityManager.createQuery("delete from GiftCertificate where id = " + id).executeUpdate();
    }
}
