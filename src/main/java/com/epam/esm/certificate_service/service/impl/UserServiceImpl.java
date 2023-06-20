package com.epam.esm.certificate_service.service.impl;

import com.epam.esm.certificate_service.dao.UserRepository;
import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.Order;
import com.epam.esm.certificate_service.entities.User;
import com.epam.esm.certificate_service.exeption_handling.exeptions.InvalidRequestParamException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.NoSuchDataException;
import com.epam.esm.certificate_service.service.GiftCertificateService;
import com.epam.esm.certificate_service.service.UserService;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final String CODE = "03";

    private final UserRepository userRepository;
    private final GiftCertificateService certificateService;

    public UserServiceImpl(UserRepository userRepository, GiftCertificateService giftCertificateService) {
        this.userRepository = userRepository;
        this.certificateService = giftCertificateService;
    }


    @Override
    @Transactional(isolation=Isolation.REPEATABLE_READ)
    public User findById(long id) {
        User user = userRepository.findById(id);

        if (user != null) {
            return user;
        } else {
            throw new NoSuchDataException("There is no user with id '" + id + "' in DB", CODE);
        }
    }

    @Override
    @Transactional(isolation=Isolation.REPEATABLE_READ)
    public User findByName(String userName) {
        try {
            return userRepository.findByFirstName(userName);
        } catch (NoResultException ex){
            throw new NoSuchDataException("There is no user with name '" + userName + "' in DB", CODE);
        }
    }

    @Override
    @Transactional(isolation=Isolation.REPEATABLE_READ)
    public List<User> getAllUsers(int pageSize, int offset) {
        if (pageSize > 0 && offset >= 0) {
            return userRepository.findAll(pageSize, offset);
        } else {
            throw new InvalidRequestParamException("Page number or size can't be 0 or negative", CODE);
        }
    }

    @Override
    @Transactional(isolation=Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    public void buyCertificate(User user, String certificateName) {
        GiftCertificate certificate = certificateService.getGiftCertificateByName(certificateName);

        Order order = new Order(certificate.getPrice(), LocalDateTime.now());
        order.setCertificate(certificate);
        order.setUser(user);

        List<Order> orders = new ArrayList<>();
        orders.add(order);

        user.setOrders(orders);

        userRepository.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public int getNumberOfRows() {
        return Math.toIntExact(userRepository.getNumberOfRows());
    }
}
