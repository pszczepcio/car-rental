package com.kodilla.carrental.service;
import com.kodilla.carrental.dao.AdditionalEquipmentDao;
import com.kodilla.carrental.domain.AdditionalEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdditionalEquipmentService {

    @Autowired
    private AdditionalEquipmentDao additionalEquipmentDao;

    public AdditionalEquipment saveEquipment(final AdditionalEquipment additionalEquipment) {
        return additionalEquipmentDao.save(additionalEquipment);
    }

    public Optional<AdditionalEquipment> getEquipment(final Long id) {
        return additionalEquipmentDao.findById(id);
    }

    public List<AdditionalEquipment> getEquipmentList() {
        return additionalEquipmentDao.findAll();
    }

    public void deleteEquipment(final Long id) {
        additionalEquipmentDao.deleteById(id);
    }
}
