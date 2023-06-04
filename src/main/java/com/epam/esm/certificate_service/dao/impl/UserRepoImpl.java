package com.epam.esm.certificate_service.dao.impl;

import com.epam.esm.certificate_service.dao.UserRepository;
import com.epam.esm.certificate_service.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserRepoImpl implements UserRepository {

    private final EntityManager entityManager;

    public UserRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByFirstName(String userName) throws NoResultException {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.firstName = :paramName", User.class);
        query.setParameter("paramName", userName);
        return query.getSingleResult();
    }

    @Override
    public User findById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.merge(user);
    }
}