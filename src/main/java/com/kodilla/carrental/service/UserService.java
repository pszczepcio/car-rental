package com.kodilla.carrental.service;

import com.kodilla.carrental.dao.UserDao;
import com.kodilla.carrental.dto.CreateUserDto;
import com.kodilla.carrental.dto.UserDto;
import com.kodilla.carrental.exception.UserNotFoundException;
import com.kodilla.carrental.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    public void saveUser(final CreateUserDto createUserDto) {
        userDao.save(userMapper.mapToUser(createUserDto));
    }

    public List<UserDto> getUsersDtoList() {
        return userMapper.getUsersDtoList(userDao.findAll());
    }

    public UserDto getUserDto(final Long id) throws UserNotFoundException {
        return userMapper.mapToUserDto(userDao.findById(id).orElseThrow(UserNotFoundException::new));
    }

    public void deleteUser(final Long id) {
        userDao.deleteById(id);
    }
}
