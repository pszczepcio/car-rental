package com.kodilla.carrental.controller;

import com.kodilla.carrental.dto.CreateUserDto;
import com.kodilla.carrental.dto.UserDto;
import com.kodilla.carrental.exception.UserNotFoundException;
import com.kodilla.carrental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/v1")
@Transactional
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public List<UserDto> getUsers(){
        return userService.getUsersDtoList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}")
    public UserDto getUserDto (@PathVariable Long userId) throws UserNotFoundException {
        return userService.getUserDto(userId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public void createUser(@RequestBody final CreateUserDto createUserDto) {
        userService.saveUser(createUserDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
