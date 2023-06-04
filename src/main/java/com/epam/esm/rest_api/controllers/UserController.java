package com.epam.esm.rest_api.controllers;

import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.User;
import com.epam.esm.certificate_service.service.TagService;
import com.epam.esm.certificate_service.service.UserService;
import com.epam.esm.rest_api.dto.Mapper;
import com.epam.esm.rest_api.dto.OrderDTO;
import com.epam.esm.rest_api.dto.TagDTO;
import com.epam.esm.rest_api.dto.UserDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final Mapper mapper;
    private final TagService tagService;

    public UserController(UserService userService, Mapper mapper, TagService tagService) {
        this.userService = userService;
        this.mapper = mapper;
        this.tagService = tagService;
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
    public List<UserDTO> getAllUsers(@RequestParam("page") int page, @RequestParam("size") int size) {
        if (size == 0 || size < 0) {
            size = 5;
        }
        if (page == 0 || page < 0) {
            page = 1;
        }
        return userService.getAllUsers(size, (page-1)*size)
                .stream().map(mapper::toUserDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/users/{id}/orders")
    public List<OrderDTO> getOrdersByUserId(@PathVariable long id) {
        return userService.findById(id).getOrders().stream().map(mapper::toOrderDto).collect(Collectors.toList());
    }

    @PostMapping(value = "/users/{id}")
    public List<OrderDTO> makeOrder(@PathVariable long id, @RequestBody GiftCertificate certificateName) {
        User user = userService.findById(id);
        userService.buyCertificate(user, certificateName.getName());
        return user.getOrders().stream().map(mapper::toOrderDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/users/{id}/mostUsedTag")
    public TagDTO getWidelyUsedTag(@PathVariable long id) {
        return mapper.toTagDto(tagService.getWidelyUsedTag(id));
    }
}
