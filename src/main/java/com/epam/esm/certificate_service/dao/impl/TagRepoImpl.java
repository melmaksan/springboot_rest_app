package com.epam.esm.certificate_service.dao.impl;

import com.epam.esm.certificate_service.dao.TagRepository;
import com.epam.esm.certificate_service.entities.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TagRepoImpl implements TagRepository {

    private final EntityManager entityManager;

    public TagRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Tag findByName(String paramName) throws NoResultException {
        TypedQuery<Tag> query = entityManager.createQuery("select t from Tag t where t.name = :paramName", Tag.class);
        query.setParameter("paramName", paramName);
        return query.getSingleResult();
    }

    @Override
    public Integer getWidelyUsedTag(long userId) {
        TypedQuery<Integer> query = entityManager.createQuery
                ("select tag.id from Tag as tag, Order as order " +
                        "join tag.certificates as certificates " +
                        "where order.certificate=certificates and order.user.id = :userId " +
                        "group by tag.id " +
                        "order by count(tag.id) desc, sum(order.price) desc, tag.id", Integer.class);
        query.setParameter("userId", userId);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    @Override
    public Tag findById(int id) {
        return entityManager.find(Tag.class, id);
    }

    @Override
    public List<Tag> findAll(int pageSize, int offset) {
        TypedQuery<Tag> query = entityManager.createQuery("select t from Tag t", Tag.class);
        query.setFirstResult(offset);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void save(Tag tag) {
        entityManager.merge(tag);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Tag tag = entityManager.find(Tag.class, id);
        entityManager.remove(tag);
    }
}
