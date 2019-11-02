package com.kodilla.carrental.service;

import com.kodilla.carrental.dao.AdditionalEquipmentDao;
import com.kodilla.carrental.dao.CarDao;
import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.exception.CarNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarDao carDao;

    @Mock
    private AdditionalEquipmentDao additionalEquipmentDao;
    private Car car;
    private Car car1;

    @Before
    public void init() {
        car = new Car().builder()
                .id(1L)
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

        car1 = new Car().builder()
                .id(2L)
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
    }

    @Test
    public void shoudlSaveCar() {
        //Given && When
        when(carDao.save(car)).thenReturn(car);
        Car carAfterSave = carService.saveCar(car);
        Long carId = carAfterSave.getId();
        String carClass = carAfterSave.getCarClass();
        String type = carAfterSave.getTypeOfCar();
        String producer = carAfterSave.getProducer();
        String model = carAfterSave.getModel();
        String color = carAfterSave.getColor();
        LocalDate date = carAfterSave.getDayOfProduction();
        boolean available = carAfterSave.isAvailability();
        int seats = carAfterSave.getNumberOfSeats();
        double prize = carAfterSave.getPricePerDay();

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
    }

    @Test
    public void shouldGetCar() {
        //Given && When
        when(carDao.findById(1L)).thenReturn(java.util.Optional.ofNullable(car));
        Optional<Car> downloadCar = carService.getCar(1L);

        //Then
        assertEquals(1L, downloadCar.get().getId().longValue());
        assertEquals("Premium", downloadCar.get().getCarClass());
        assertEquals("Sedan", downloadCar.get().getTypeOfCar());
        assertEquals("Ford", downloadCar.get().getProducer());
        assertEquals("Focus", downloadCar.get().getModel());
        assertEquals(true, downloadCar.get().isAvailability());
        assertEquals(5, downloadCar.get().getNumberOfSeats());
        assertEquals("white", downloadCar.get().getColor());
        assertEquals(350, downloadCar.get().getPricePerDay(), 0.01);
        assertEquals(LocalDate.of(2010,1,1) , downloadCar.get().getDayOfProduction());
    }

    @Test
    public void shouldGetCarsList() {
        //Given
        List<Car> carList = new ArrayList<>();
        carList.add(car);
        carList.add(car1);

        //When
        when(carDao.findAll()).thenReturn(carList);
        List<Car> cars = carService.getCarsList();
        int carsListSize = carList.size();

        //Then
        assertEquals(2, carsListSize);
        assertEquals("Premium", cars.get(0).getCarClass());
        assertEquals("Basic", cars.get(1).getCarClass());
    }

    @Test
    public void shouldUpdateStatus() throws CarNotFoundException {
        //Given
        Car carWithNewStatus = new Car().builder()
                .id(1L)
                .carClass("Premium")
                .typeOfCar("Sedan")
                .producer("Ford")
                .model("Focus")
                .availability(false)
                .numberOfSeats(5)
                .color("white")
                .pricePerDay(350)
                .dayOfProduction(LocalDate.of(2010,1,1))
                .build();

        //When && then
        when(carDao.findById(1L)).thenReturn(ofNullable(car));
        when(carDao.save(car)).thenReturn(carWithNewStatus);
        Car newStatus = carService.updateStatus(1L, false);
        assertEquals(false, newStatus.isAvailability());
    }

    @Test
    public void shuldUpdatePrize() throws CarNotFoundException {
        //Given
        Car carWithChangePrize = new Car().builder()
                .id(1L)
                .carClass("Premium")
                .typeOfCar("Sedan")
                .producer("Ford")
                .model("Focus")
                .availability(true)
                .numberOfSeats(5)
                .color("white")
                .pricePerDay(200)
                .dayOfProduction(LocalDate.of(2010,1,1))
                .build();

        Long id = 1L;
        double newPrize = 200;

        //When
        when(carDao.findById(id)).thenReturn(ofNullable(car));
        when(carDao.save(car)).thenReturn(carWithChangePrize);
        Car carWithNewPrize = carService.updatePrize(id, newPrize);

        //Then
        assertEquals(1L, carWithNewPrize.getId().longValue());
        assertEquals(200, carWithNewPrize.getPricePerDay(), 0.01);
    }

    @Test
    public void shouldDeleteCar()  {
        //Given
        Long id = 1L;
        carService.deleteCar(id);
        verify(carDao, times(1)).deleteById(id);
        //Then
    }
}