package com.epam.esm.rest_api.controllers;

import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.Order;
import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.entities.User;
import com.epam.esm.certificate_service.service.TagService;
import com.epam.esm.certificate_service.service.UserService;
import com.epam.esm.rest_api.dto.Mapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    private MockMvc mockMvc;
    @Mock
    private TagService tagService;
    @Mock
    private UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Mapper mapper = new Mapper();
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService, mapper, tagService)).build();

        user = new User("test", "testing", "test@org.com");
        user.setId(1);
        user.setOrders(getOrdersList());
    }

    private List<Order> getOrdersList() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order(100, LocalDateTime.now());
        order.setCertificate(getCertificate());
        Order order2 = new Order(1000, LocalDateTime.now(ZoneId.of("+5")));
        order2.setCertificate(getCertificate());
        orders.add(order);
        orders.add(order2);

        return orders;
    }

    private GiftCertificate getCertificate() {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(1);
        certificate.setName("certificate");
        certificate.setDuration(10);
        certificate.setPrice(100);
        certificate.setDescription("test certificate");
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        return certificate;
    }

    @Test
    void getUserById() throws Exception {
        when(userService.findById(anyLong())).thenReturn(user);

        this.mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())));
    }

    @Test
    void getUserByName() throws Exception {
        when(userService.findByName(anyString())).thenReturn(user);

        this.mockMvc.perform(get("/api/users/findByName/{name}", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())));
    }

    @Test
    void getAllUsers() throws Exception {
        List<User> users = getUsersList();

        when(userService.getAllUsers(5, 0)).thenReturn(users);

        this.mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(users.size())))
                .andDo(print());
    }

    private List<User> getUsersList() {
        List<User> users = new ArrayList<>();
        User user2 = new User("user", "second", "user2@org.com");
        user2.setId(2);
        users.add(user);
        users.add(user2);

        return users;
    }

    @Test
    void getOrdersByUserId() throws Exception {
        List<Order> orders = user.getOrders();

        when(userService.findById(anyLong())).thenReturn(user);

        this.mockMvc.perform(get("/api/users/{id}/orders", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(orders.size())))
                .andDo(print());
    }

    @Test
    void makeOrder() throws Exception {
        when(userService.findById(anyLong())).thenReturn(user);
        doNothing().when(userService).buyCertificate(user, getCertificate().getName());

        List<Order> orders = user.getOrders();
        when(userService.findById(anyLong())).thenReturn(user);

        this.mockMvc.perform(post("/api/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getCertificate())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(orders.size())))
                .andDo(print());
    }

    @Test
    void getWidelyUsedTag() throws Exception {
        Tag tag = new Tag("most_used_tag");
        tag.setId(5);

        when(tagService.getWidelyUsedTag(anyLong())).thenReturn(tag);

        this.mockMvc.perform(get("/api/users/{id}/mostUsedTag", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(tag.getName())));
    }
}