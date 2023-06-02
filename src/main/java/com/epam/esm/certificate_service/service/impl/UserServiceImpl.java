package com.epam.esm.certificate_service.service.impl;

import com.epam.esm.certificate_service.dao.UserRepository;
import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.Order;
import com.epam.esm.certificate_service.entities.User;
import com.epam.esm.certificate_service.exeption_handling.exeptions.NoSuchDataException;
import com.epam.esm.certificate_service.service.GiftCertificateService;
import com.epam.esm.certificate_service.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public User findById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new NoSuchDataException("There is no user with id '" + id + "' in DB", CODE);
        }

        return optionalUser.get();
    }

    @Override
    public User findByName(String userName) {
        Optional<User> optionalUser = userRepository.findByFirstName(userName);

        if (optionalUser.isEmpty()) {
            throw new NoSuchDataException("There is no user with name '" + userName + "' in DB", CODE);
        }

        return optionalUser.get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
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
}
