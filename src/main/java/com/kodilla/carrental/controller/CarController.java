package com.kodilla.carrental.controller;

import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.dto.*;
import com.kodilla.carrental.exception.AdditionalEquipmentNotFoundException;
import com.kodilla.carrental.exception.CarNotFoundException;
import com.kodilla.carrental.mapper.CarMapper;
import com.kodilla.carrental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1")
@Transactional
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private CarMapper carMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/cars")
    public List<CarDto> getCars() {
        return carMapper.getCarsDtoList(carService.getCarsList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cars/{carId}")
    public GetCarDto getCar(@PathVariable Long carId) throws CarNotFoundException {
        return carMapper.mapToGetCarDto(carService.getCar(carId).orElseThrow(CarNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cars", consumes = APPLICATION_JSON_VALUE)
    public Car createCar(@RequestBody CreateCarDto createCarDto) {
        return carService.saveCar(carMapper.mapToCar(createCarDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/cars")
    public GetCarDto updateCarStatus(@RequestBody CarUpdateStatusDto carUpdateStatusDto) throws CarNotFoundException {
       return carMapper.mapToGetCarDto(carService.updateStatus(carUpdateStatusDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/car")
    public GetCarDto updateCarEquipment(@RequestBody UpdateCarAndEquipment updateCarAndEquipment) throws CarNotFoundException, AdditionalEquipmentNotFoundException {
         return carMapper.mapToGetCarDto(carService.addEquipmentToCar(updateCarAndEquipment));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/car/equipment")
    public GetCarDto deleteEquipmentFromCar(@RequestBody UpdateCarAndEquipment updateCarAndEquipment) throws CarNotFoundException, AdditionalEquipmentNotFoundException {
        return carMapper.mapToGetCarDto(carService.deleteEquipmentFromCar(updateCarAndEquipment));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/cars/{carId}")
    public void deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/cars/{carId}")
    public GetCarDto returningTheCar (@PathVariable Long carId) throws CarNotFoundException, AdditionalEquipmentNotFoundException {
        return carMapper.mapToGetCarDto(carService.returnCarToRental(carId));
    }
}
