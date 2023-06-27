package com.epam.esm.certificate_service.service;

import com.epam.esm.certificate_service.utils.DataGenerator;
import com.epam.esm.certificate_service.entities.Order;
import com.epam.esm.certificate_service.entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@MockBeans(@MockBean(DataGenerator.class))
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    private static User user1;
    private static User user2;

    @BeforeAll
    static void setUp() {
        user1 = new User("Petro", "Bumper", "bamper@org.com");
        user1.setId(1);

        user2 = new User("Max", "Pain", "pain@org.com");
        user2.setId(2);
    }

    @Test
    void findById() {
        User foundUser = userService.findById(user1.getId());

        assertNotNull(foundUser);
        assertEquals(user1, foundUser);
    }

    @Test
    void findByName() {
        User foundUser = userService.findByName(user2.getFirstName());

        assertNotNull(foundUser);
        assertEquals(user2, foundUser);
    }

    @Test
    void getAllUsers() {
        List<User> tags = getUserList();
        List<User> foundList = userService.getAllUsers(2, 0);

        assertNotEquals(true, foundList.isEmpty());
        assertEquals(tags, foundList);
    }

    private List<User> getUserList() {
        List<User> tags = new ArrayList<>();
        tags.add(user1);
        tags.add(user2);
        return tags;
    }

    @Test
    void buyCertificate() {
        List<Order> orders = userService.findById(user1.getId()).getOrders();

        userService.buyCertificate(user1, "Promo_500");

        List<Order> expectedOrders = userService.findById(user1.getId()).getOrders();

        assertNotEquals(true, expectedOrders.isEmpty());
        assertEquals(orders.size(), 3);
        assertEquals(expectedOrders.size(), 4);

    }
}