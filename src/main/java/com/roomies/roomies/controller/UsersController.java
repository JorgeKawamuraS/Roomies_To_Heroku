package com.roomies.roomies.controller;

import com.roomies.roomies.domain.model.User;
import com.roomies.roomies.domain.service.UserService;
import com.roomies.roomies.resource.SaveUserResource;
import com.roomies.roomies.resource.UserResource;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UsersController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @Operation(tags = {"users"})
    @GetMapping("/users")
    public Page<UserResource> getAllUsers(Pageable pageable){
        Page<User> userPage = userService.getAllUsers(pageable);
        List<UserResource> resources = userPage.getContent().stream().map(
                this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources,pageable,resources.size());
    }

    @Operation(tags = {"users"})
    @PostMapping("/users")
    public UserResource createUser(@Valid @RequestBody SaveUserResource resource){
        return convertToResource(userService.createUser(convertToEntity(resource)));
    }

    private User convertToEntity(SaveUserResource resource) {
        return mapper.map(resource, User.class);
    }

    private UserResource convertToResource(User entity){
        return mapper.map(entity, UserResource.class);
    }


}
