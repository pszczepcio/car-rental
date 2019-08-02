package com.kodilla.carrental.service;

import com.kodilla.carrental.dao.AdditionalEquipmentDao;
import com.kodilla.carrental.dao.CarDao;
import com.kodilla.carrental.domain.AdditionalEquipment;
import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.dto.*;
import com.kodilla.carrental.exception.AdditionalEquipmentNotFoundException;
import com.kodilla.carrental.exception.CarNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class CarService {

    @Autowired
    private CarDao carDao;

    @Autowired
    private AdditionalEquipmentDao additionalEquipmentDao;

    public Car saveCar(final Car car) {
        return carDao.save(car);
    }

    public List<Car> getCarsList () {
        return carDao.findAll();
    }

    public Optional<Car> getCar(final Long id) {
        return carDao.findById(id);
    }

    public Car updateStatus(final Long id, final boolean status) throws CarNotFoundException {
        Car car = carDao.findById(id).orElseThrow(CarNotFoundException::new);
        car.setAvailability(status);
        return carDao.save(car);
    }

    public void addEquipmentToCar(final UpdateCarAndEquipment updateCarAndEquipment) throws AdditionalEquipmentNotFoundException, CarNotFoundException {
         addEquipment(updateCarAndEquipment);
    }

    public Car deleteEquipmentFromCar(final UpdateCarAndEquipment updateCarAndEquipment) throws AdditionalEquipmentNotFoundException, CarNotFoundException {
        return deleteEquipment(updateCarAndEquipment);
    }

    public void deleteCar(final Long carId) {
        carDao.deleteById(carId);
    }

    public Car returnCarToRental(final Long carId) throws CarNotFoundException, AdditionalEquipmentNotFoundException {
        return returnCar(carId);
    }

    private Car deleteEquipment(final UpdateCarAndEquipment updateCarAndEquipment) throws AdditionalEquipmentNotFoundException, CarNotFoundException {

        for (int i = 0 ; i < updateCarAndEquipment.getEguipmentIdList().size() ; i++){
            AdditionalEquipment additionalEquipment = additionalEquipmentDao.findById(updateCarAndEquipment.getEguipmentIdList().get(i)).
                    orElseThrow(AdditionalEquipmentNotFoundException::new);
            additionalEquipment.getCarsList().remove(carDao.findById(updateCarAndEquipment.getCarId()).
                    orElseThrow(CarNotFoundException::new));
            additionalEquipmentDao.save(additionalEquipment);
        }
        Car car = carDao.findById(updateCarAndEquipment.getCarId()).orElseThrow(CarNotFoundException::new);
        for (int i = 0 ; i < updateCarAndEquipment.getEguipmentIdList().size() ; i++) {
            car.getEquipments().remove(additionalEquipmentDao.findById(updateCarAndEquipment.getEguipmentIdList().get(i)).orElseThrow(AdditionalEquipmentNotFoundException::new));
        }
        return carDao.save(car);
    }

    private void addEquipment (final UpdateCarAndEquipment updateCarAndEquipment) throws AdditionalEquipmentNotFoundException, CarNotFoundException {
        for (int i = 0 ; i < updateCarAndEquipment.getEguipmentIdList().size() ; i++){
            AdditionalEquipment additionalEquipment = additionalEquipmentDao.findById(updateCarAndEquipment.getEguipmentIdList().get(i)).
                    orElseThrow(AdditionalEquipmentNotFoundException::new);
            additionalEquipment.getCarsList().add(carDao.findById(updateCarAndEquipment.getCarId()).
                    orElseThrow(CarNotFoundException::new));
            additionalEquipmentDao.save(additionalEquipment);
        }
        Car car = (carDao.findById(updateCarAndEquipment.getCarId()).orElseThrow(CarNotFoundException::new));
        for (int i = 0 ; i < updateCarAndEquipment.getEguipmentIdList().size() ; i++) {
            car.builder()
                    .equipments(Collections.singletonList(additionalEquipmentDao.findById(updateCarAndEquipment.
                            getEguipmentIdList().get(i)).orElseThrow(AdditionalEquipmentNotFoundException::new)))
                    .build();
        }
        carDao.save(car);
    }

    private Car returnCar (final Long carId) throws CarNotFoundException, AdditionalEquipmentNotFoundException {
        Car car = carDao.findById(carId).orElseThrow(CarNotFoundException::new);
        car.setAvailability(true);
        int equipmentListSize = car.getEquipments().size();
        for (int i = 0 ; i < equipmentListSize ; i++) {
            Long equipmentId = car.getEquipments().get(i).getId();
            AdditionalEquipment additionalEquipment = additionalEquipmentDao.findById(equipmentId).orElseThrow(AdditionalEquipmentNotFoundException::new);
            additionalEquipment.getCarsList().remove(car);
            additionalEquipmentDao.save(additionalEquipment);
        }
        car.setEquipments(new ArrayList<>());
        return carDao.save(car);
    }
}
