package com.epam.esm.rest_api.controllers;

import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.Order;
import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.entities.User;
import com.epam.esm.certificate_service.service.TagService;
import com.epam.esm.certificate_service.service.UserService;
import com.epam.esm.rest_api.dto.Mapper;
import com.epam.esm.rest_api.dto.OrderDTO;
import com.epam.esm.rest_api.dto.TagDTO;
import com.epam.esm.rest_api.dto.UserDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/users", produces = MediaTypes.HAL_JSON_VALUE)
public class UserController {

    private final UserService userService;
    private final Mapper mapper;
    private final TagService tagService;

    public UserController(UserService userService, Mapper mapper, TagService tagService) {
        this.userService = userService;
        this.mapper = mapper;
        this.tagService = tagService;
    }

    @GetMapping(value = "/{id}")
    public UserDTO getUserById(@PathVariable long id) {
        return mapper.toUserDto(userService.findById(id));
    }

    @GetMapping(value = "/findByName/{name}")
    public EntityModel<UserDTO> getUserByName(@PathVariable String name) {
        UserDTO userDTO = mapper.toUserDto(userService.findByName(name));
        Link link;
        try {
            link = linkTo(UserController.class,
                    UserController.class.getMethod("getUserByName", String.class), name)
                    .withRel("get user with '" + name + "' name");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

        return EntityModel.of(userDTO).add(link);
    }

    @GetMapping
    public CollectionModel<UserDTO> getAllUsers(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        List<User> users = userService.getAllUsers(size, (page - 1) * size);

        List<UserDTO> dtoList = users.stream().map(mapper::toUserDto).collect(Collectors.toList());
        Link link = linkTo(methodOn(UserController.class).getAllUsers(page, size)).withSelfRel();

        return CollectionModel.of(dtoList).add(link);
    }

    @GetMapping(value = "/{id}/orders")
    public CollectionModel<OrderDTO> getOrdersByUserId(@PathVariable long id) {
        List<Order> orders = userService.findById(id).getOrders();

        List<OrderDTO> dtoList = orders.stream().map(mapper::toOrderDto).collect(Collectors.toList());
        Link link = linkTo(methodOn(UserController.class).getOrdersByUserId(id)).withSelfRel();

        return CollectionModel.of(dtoList).add(link);
    }

    @PostMapping(value = "/{id}")
    public CollectionModel<OrderDTO> makeOrder(@PathVariable long id, @RequestBody GiftCertificate certificateName) {
        User user = userService.findById(id);
        userService.buyCertificate(user, certificateName.getName());
        return getOrdersByUserId(id);
    }

    @GetMapping(value = "/{id}/mostUsedTag")
    public EntityModel<TagDTO> getWidelyUsedTag(@PathVariable long id) {
        TagDTO dto = mapper.toTagDto(tagService.getWidelyUsedTag(id));
        Link link;
        try {
            link = linkTo(UserController.class,
                    UserController.class.getMethod("getWidelyUsedTag", long.class), id)
                    .withRel("show most user used tag with id " + id);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

        return EntityModel.of(dto).add(link);
    }
}
