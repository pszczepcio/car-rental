package com.kodilla.carrental.dao;

import com.kodilla.carrental.domain.AdditionalEquipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditionalEquipmentDao extends CrudRepository<AdditionalEquipment, Long> {
    @Override
    List<AdditionalEquipment> findAll();
}
