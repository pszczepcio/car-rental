package com.kodilla.carrental.service;

import com.kodilla.carrental.dao.AdditionalEquipmentDao;
import com.kodilla.carrental.domain.AdditionalEquipment;
import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.dto.UpdateCarAndEquipment;
import com.kodilla.carrental.exception.AdditionalEquipmentNotFoundException;
import com.kodilla.carrental.exception.CarNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CarServiceTest {

    @Autowired
    private CarService carService;

    @Mock
    private AdditionalEquipmentDao additionalEquipmentDao;

    @Mock
    private AdditionalEquipmentService equipmentService;

    @Test
    public void shoudlSaveCar() throws CarNotFoundException {
        //Given
        Car car = new Car().builder()
                .carClass("Premium")
                .typeOfCar("Sedan")
                .producer("Ford")
                .model("Focus")
                .availability(true)
                .numberOfSeats(5)
                .color("white")
                .pricePerDay(350)
                .dayOfProduction(LocalDate.of(2010,1,1))
                .build();

        //When
        carService.saveCar(car);
        Long carId = car.getId();
        String carClass = carService.getCar(carId).orElseThrow(CarNotFoundException::new).getCarClass();
        String type = carService.getCar(carId).orElseThrow(CarNotFoundException::new).getTypeOfCar();
        String producer = carService.getCar(carId).orElseThrow(CarNotFoundException::new).getProducer();
        String model = carService.getCar(carId).orElseThrow(CarNotFoundException::new).getModel();
        String color = carService.getCar(carId).orElseThrow(CarNotFoundException::new).getColor();
        LocalDate date = carService.getCar(carId).orElseThrow(CarNotFoundException::new).getDayOfProduction();
        boolean available = carService.getCar(carId).orElseThrow(CarNotFoundException::new).isAvailability();
        int seats = carService.getCar(carId).orElseThrow(CarNotFoundException::new).getNumberOfSeats();
        double prize = carService.getCar(carId).orElseThrow(CarNotFoundException::new).getPricePerDay();

        //Then
        assertNotNull(carId);
        assertEquals("Premium", carClass);
        assertEquals("Sedan", type);
        assertEquals("Ford", producer);
        assertEquals("Focus", model);
        assertEquals("white", color);
        assertEquals("2010-01-01", date.toString());
        assertEquals(true, available);
        assertEquals(5, seats);
        assertEquals(350, prize, 0.1);

        //Cleanup
        carService.deleteCar(car.getId());


    }

    @Test
    public void getCarsList() {
        //Given
        Car car = new Car().builder()
                .carClass("Premium")
                .typeOfCar("Sedan")
                .producer("Ford")
                .model("Focus")
                .availability(true)
                .numberOfSeats(5)
                .color("white")
                .pricePerDay(350)
                .dayOfProduction(LocalDate.of(2010,1,1))
                .build();

        Car car1 = new Car().builder()
                .carClass("Basic")
                .typeOfCar("Hatchback")
                .producer("Fiat")
                .model("500")
                .availability(true)
                .numberOfSeats(4)
                .color("black")
                .pricePerDay(400)
                .dayOfProduction(LocalDate.of(2015,2,10))
                .build();

        //When
        carService.saveCar(car);
        carService.saveCar(car1);
        List<Car> carList = carService.getCarsList();
        int carsListSize = carList.size();

        //Then
        assertNotNull(car.getId());
        assertNotNull(car1.getId());
        assertEquals(2 , carsListSize);

        //Clean up
        carService.deleteCar(car1.getId());
        carService.deleteCar(car.getId());
    }

    @Test
    public void updateStatus() throws CarNotFoundException {
        //Given
        Car car = new Car().builder()
                .carClass("Premium")
                .typeOfCar("Sedan")
                .producer("Ford")
                .model("Focus")
                .availability(true)
                .numberOfSeats(5)
                .color("white")
                .pricePerDay(350)
                .dayOfProduction(LocalDate.of(2010,1,1))
                .build();
        carService.saveCar(car);
        Long carId = car.getId();
        boolean status = false;

        //When
        carService.updateStatus(carId, status);
        boolean statusAfterUpdate = carService.getCar(carId).orElseThrow(CarNotFoundException::new).isAvailability();

        //Then
        assertEquals(status, statusAfterUpdate);

        //Cleanup
        carService.deleteCar(carId);
    }

    @Test
    public void deleteEquipmentFromCar() {
    }


    @Test
    public void returnCarToRental() {
    }
}