package com.kodilla.carrental.dao;

import com.kodilla.carrental.domain.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarDao extends CrudRepository<Car, Long> {

    @Override
    List<Car> findAll();
}
