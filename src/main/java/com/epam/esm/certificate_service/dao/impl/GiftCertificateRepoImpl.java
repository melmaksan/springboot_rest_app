package com.epam.esm.certificate_service.dao.impl;

import com.epam.esm.certificate_service.dao.GiftCertificateRepository;
import com.epam.esm.certificate_service.entities.GiftCertificate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public class GiftCertificateRepoImpl implements GiftCertificateRepository {

    private final EntityManager entityManager;

    public GiftCertificateRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public GiftCertificate findById(long id) {
        return entityManager.find(GiftCertificate.class, id);
    }

    @Override
    public GiftCertificate findByName(String paramName) throws NoResultException {
        TypedQuery<GiftCertificate> query = entityManager.createQuery
                ("select g from GiftCertificate g where g.name = :paramName", GiftCertificate.class);
        query.setParameter("paramName", paramName);
        return query.getSingleResult();
    }

    @Override
    public List<GiftCertificate> findAll() {
        return entityManager.createQuery("select g from GiftCertificate g",
                GiftCertificate.class).getResultList();
    }

    @Override
    @Transactional
    public void saveOrUpdate(GiftCertificate certificate) {
        entityManager.merge(certificate);
    }

    @Override
    public List<GiftCertificate> findByPart(String part) {
        TypedQuery<GiftCertificate> query = entityManager.createQuery
                ("select g from GiftCertificate g WHERE g.name LIKE :part OR g.description LIKE :part",
                        GiftCertificate.class);
        query.setParameter("part", "%" + part + "%");
        return query.getResultList();
    }

    @Override
    public List<GiftCertificate> ascByDate() {
        return entityManager.createQuery("select g from GiftCertificate g ORDER BY g.createDate ASC",
                GiftCertificate.class).getResultList();
    }

    @Override
    public List<GiftCertificate> descByDate() {
        return entityManager.createQuery("select g from GiftCertificate g ORDER BY g.createDate DESC",
                GiftCertificate.class).getResultList();
    }

    @Override
    public List<GiftCertificate> ascByName() {
        return entityManager.createQuery("select g from GiftCertificate g ORDER BY g.name ASC",
                GiftCertificate.class).getResultList();
    }

    @Override
    public List<GiftCertificate> descByName() {
        return entityManager.createQuery("select g from GiftCertificate g ORDER BY g.name DESC",
                GiftCertificate.class).getResultList();
    }

    @Override
    @Transactional
    public void deleteCertificate(long id) {
        GiftCertificate certificate = entityManager.find(GiftCertificate.class, id);
        entityManager.remove(certificate);
    }
}
