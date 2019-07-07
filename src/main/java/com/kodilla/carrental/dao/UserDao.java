package com.kodilla.carrental.dao;

import com.kodilla.carrental.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends CrudRepository<User, Long> {

    @Override
    List<User> findAll();
}
