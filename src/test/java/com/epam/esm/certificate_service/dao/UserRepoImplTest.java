package com.epam.esm.certificate_service.dao;

import com.epam.esm.certificate_service.utils.DataGenerator;
import com.epam.esm.certificate_service.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@MockBeans(@MockBean(DataGenerator.class))
class UserRepoImplTest {

    @Autowired
    private UserRepository userRepo;

    @Test
    void findByFirstName() {
        User user = userRepo.findByFirstName("Petro");

        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("Bumper", user.getSurname());
    }

    @Test
    void findById() {
        User user = userRepo.findById(2);

        assertNotNull(user);
        assertEquals("Max", user.getFirstName());
        assertEquals("pain@org.com", user.getEmail());
    }

    @Test
    void findAll() {
        List<User> userList = userRepo.findAll(5, 0);

        assertNotEquals(true, userList.isEmpty());
        assertEquals(2, userList.size(), "should be 2 users in the list");
    }

    @Test
    @Transactional
    void save() {
        User user = new User("test", "testing", "test@org.com");
        userRepo.save(user);

        List<User> userList = userRepo.findAll(5, 0);

        assertNotEquals(true, userList.isEmpty());
        assertEquals(3, userList.size(), "should be 3 users in the list");
    }
}