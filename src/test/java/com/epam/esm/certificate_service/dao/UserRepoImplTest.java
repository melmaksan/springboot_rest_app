package com.epam.esm.certificate_service.dao;

import com.epam.esm.certificate_service.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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

        assertNotNull(userList);
        assertEquals(2, userList.size(), "should be 2 users in the list");
    }

    @Test
    void save() {
        User user = new User("test", "test", "test@org.com");
        userRepo.save(user);

        List<User> userList = userRepo.findAll(5, 0);

        assertEquals(3, userList.size(), "should be 3 users in the list");
    }
}