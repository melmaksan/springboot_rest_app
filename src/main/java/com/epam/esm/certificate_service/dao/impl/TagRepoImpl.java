package com.epam.esm.certificate_service.dao.impl;

import com.epam.esm.certificate_service.dao.TagRepository;
import com.epam.esm.certificate_service.entities.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
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
        Query query = entityManager.createQuery("from Tag where name=:paramName");
        query.setParameter("paramName", paramName);
        return (Tag)(query.getSingleResult());
    }

    @Override
    public Integer getWidelyUsedTag(long userId) {
        Query query = entityManager.createQuery
                ("select tag.id from Tag as tag, Order as order \n" +
                        "\t join tag.certificates as certificates \n" +
                        "\t where order.certificate=certificates and order.user.id=:userId \n" +
                        "\t GROUP BY  tag.id \n" +
                        "\t order by count(tag.id) desc, sum(order.price) desc, tag.id");
        query.setParameter("userId", userId);
        query.setMaxResults(1);
        return (Integer) (query.getSingleResult());
    }

    @Override
    public Tag findById(int id) {
        return entityManager.find(Tag.class, id);
    }

    @Override
    public List<Tag> findAll() {
        return (List<Tag>) entityManager.createQuery("from Tag").getResultList();
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
