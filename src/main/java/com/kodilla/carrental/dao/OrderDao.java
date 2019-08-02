package com.kodilla.carrental.dao;

import com.kodilla.carrental.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDao extends CrudRepository<Order, Long> {

    @Override
    List<Order> findAll();
}
