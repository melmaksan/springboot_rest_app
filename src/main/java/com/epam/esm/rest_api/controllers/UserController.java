package com.epam.esm.rest_api.controllers;

import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.Order;
import com.epam.esm.certificate_service.entities.User;
import com.epam.esm.certificate_service.service.UserService;
import com.epam.esm.rest_api.dto.Mapper;
import com.epam.esm.rest_api.dto.UserDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final Mapper mapper;

    public UserController(UserService userService, Mapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/users/{id}")
    public UserDTO getUserById(@PathVariable long id) {
        return mapper.toUserDto(userService.findById(id));
    }

    @GetMapping(value = "/users/findByName/{name}")
    public UserDTO getUserByName(@PathVariable String name) {
        return mapper.toUserDto(userService.findByName(name));
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers()
                .stream().map(mapper::toUserDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/users/{id}/orders")
    public List<Order> getOrdersByUserId(@PathVariable long id) {
        return userService.findById(id).getOrders();
    }

    @PostMapping(value = "/users/{id}")
    public List<Order> makeOrder(@PathVariable long id, @RequestBody String certificateName) {
        User user = userService.findById(id);
        System.out.println(certificateName);
        userService.buyCertificate(user, certificateName);
        return user.getOrders();
    }
}
