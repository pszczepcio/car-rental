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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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

    public Car updateStatus(final CarUpdateStatusDto carUpdateStatusDto) throws CarNotFoundException {
        Car car = carDao.findById(carUpdateStatusDto.getId()).orElseThrow(CarNotFoundException::new);
        car.setAvailability(carUpdateStatusDto.isAvailability());
        return carDao.save(car);
    }

    public Car addEquipmentToCar(final UpdateCarAndEquipment updateCarAndEquipment) throws AdditionalEquipmentNotFoundException, CarNotFoundException {
        return addEquipment(updateCarAndEquipment);
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
            car.getAdditionalEquipmentList().remove(additionalEquipmentDao.findById(updateCarAndEquipment.getEguipmentIdList().get(i)).orElseThrow(AdditionalEquipmentNotFoundException::new));
        }
        return carDao.save(car);
    }

    private Car addEquipment (final UpdateCarAndEquipment updateCarAndEquipment) throws AdditionalEquipmentNotFoundException, CarNotFoundException {

        for (int i = 0 ; i < updateCarAndEquipment.getEguipmentIdList().size() ; i++){
            AdditionalEquipment additionalEquipment = additionalEquipmentDao.findById(updateCarAndEquipment.getEguipmentIdList().get(i)).
                    orElseThrow(AdditionalEquipmentNotFoundException::new);
            additionalEquipment.getCarsList().add(carDao.findById(updateCarAndEquipment.getCarId()).
                    orElseThrow(CarNotFoundException::new));
            additionalEquipmentDao.save(additionalEquipment);
        }

        Car car = carDao.findById(updateCarAndEquipment.getCarId()).orElseThrow(CarNotFoundException::new);
        for (int i = 0 ; i < updateCarAndEquipment.getEguipmentIdList().size() ; i++) {
            car.getAdditionalEquipmentList().add(additionalEquipmentDao.findById(updateCarAndEquipment.getEguipmentIdList().get(i)).orElseThrow(AdditionalEquipmentNotFoundException::new));
        }
        return carDao.save(car);
    }

    private Car returnCar (final Long carId) throws CarNotFoundException, AdditionalEquipmentNotFoundException {
        Car car = carDao.findById(carId).orElseThrow(CarNotFoundException::new);
        car.setAvailability(true);
        int equipmentListSize = car.getAdditionalEquipmentList().size();
        for (int i = 0 ; i < equipmentListSize ; i++) {
            Long equipmentId = car.getAdditionalEquipmentList().get(i).getId();
            AdditionalEquipment additionalEquipment = additionalEquipmentDao.findById(equipmentId).orElseThrow(AdditionalEquipmentNotFoundException::new);
            additionalEquipment.getCarsList().remove(car);
            additionalEquipmentDao.save(additionalEquipment);
        }
        car.setAdditionalEquipmentList(new ArrayList<>());
        return carDao.save(car);
    }
}
