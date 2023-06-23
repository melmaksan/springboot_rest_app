package com.epam.esm.certificate_service.service.impl;

import com.epam.esm.certificate_service.dao.UserRepository;
import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.Order;
import com.epam.esm.certificate_service.entities.User;
import com.epam.esm.certificate_service.exeption_handling.exeptions.EmptyRequestBodyException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.InvalidRequestParamException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.NoSuchDataException;
import com.epam.esm.certificate_service.service.GiftCertificateService;
import com.epam.esm.certificate_service.service.UserService;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final String CODE = "03";

    private final UserRepository userRepository;
    private final GiftCertificateService certificateService;

    public UserServiceImpl(UserRepository userRepository, GiftCertificateService giftCertificateService) {
        this.userRepository = userRepository;
        this.certificateService = giftCertificateService;
    }


    @Override
    public User findById(long id) {
        User user = userRepository.findById(id);

        if (user != null) {
            return user;
        } else {
            throw new NoSuchDataException("There is no user with id '" + id + "' in DB", CODE);
        }
    }

    @Override
    public User findByName(String userName) {
        try {
            return userRepository.findByFirstName(userName);
        } catch (NoResultException ex){
            throw new NoSuchDataException("There is no user with name '" + userName + "' in DB", CODE);
        }
    }

    @Override
    public List<User> getAllUsers(int pageSize, int offset) {
        if (pageSize > 0 && offset >= 0) {
            return userRepository.findAll(pageSize, offset);
        } else {
            throw new InvalidRequestParamException("Page number or size can't be 0 or negative", CODE);
        }
    }

    @Override
    @Transactional(readOnly = false, isolation=Isolation.SERIALIZABLE)
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
    public int getNumberOfRows() {
        return Math.toIntExact(userRepository.getNumberOfRows());
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
    public void addUser(User user) {
        if (user.getFirstName() == null || user.getSurname() == null || user.getEmail() == null) {
            throw new EmptyRequestBodyException("Field name, surname and email are required, " +
                    "please try again!", CODE);
        }
        userRepository.save(user);
    }
}
