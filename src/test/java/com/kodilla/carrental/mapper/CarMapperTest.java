package com.kodilla.carrental.mapper;

import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.dto.CarDto;
import com.kodilla.carrental.dto.CreateCarDto;
import com.kodilla.carrental.dto.GetCarDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarMapperTest {

    @Autowired
    private CarMapper carMapper;

    @Test
    public void mapToCar() {
        //Given
        CreateCarDto carDto = new CreateCarDto(
                "Premium",
                "Sedan",
                "Skoda",
                "Superb",
                LocalDate.of(2015, 4, 23),
                450,
                "Black",
                5,
                true);

        //When
        Car car = carMapper.mapToCar(carDto);

        //Then
        assertEquals("Premium", car.getCarClass());
        assertEquals("Sedan", car.getTypeOfCar());
        assertEquals("Skoda", car.getProducer());
        assertEquals("Superb", car.getModel());
        assertEquals("2015-04-23", car.getDayOfProduction().toString());
        assertEquals(450, car.getPricePerDay(), 0.1);
        assertEquals("Black", car.getColor());
        assertEquals(5, car.getNumberOfSeats());
        assertTrue(car.isAvailability());
    }

    @Test
    public void getCarsDtoList() {
        //Given
        Car car = new Car().builder()
                .id(1L)
                .carClass("Premium")
                .typeOfCar("Sedan")
                .producer("Ford")
                .model("Focus")
                .availability(true)
                .numberOfSeats(5)
                .color("white")
                .pricePerDay(350)
                .dayOfProduction(LocalDate.of(2010, 1, 1))
                .build();

        Car car1 = new Car().builder()
                .id(2L)
                .carClass("Basic")
                .typeOfCar("Hatchback")
                .producer("Fiat")
                .model("500")
                .availability(true)
                .numberOfSeats(4)
                .color("black")
                .pricePerDay(400)
                .dayOfProduction(LocalDate.of(2015, 2, 10))
                .build();

        List<Car> carList = new ArrayList<>();
        carList.add(car);
        carList.add(car1);

        //When
        List<CarDto> carDtoList = carMapper.getCarsDtoList(carList);

        //Then
        assertEquals(2, carDtoList.size());
        assertEquals(1L, carDtoList.get(0).getId().longValue());
        assertEquals(2L, carDtoList.get(1).getId().longValue());
        assertEquals("white", carDtoList.get(0).getColor());
        assertEquals("black", carDtoList.get(1).getColor());
    }

    @Test
    public void mapToGetCarDto() {
        //Given
        Car car = new Car().builder()
                .id(1L)
                .carClass("Premium")
                .typeOfCar("Sedan")
                .producer("Ford")
                .model("Focus")
                .availability(true)
                .numberOfSeats(5)
                .color("white")
                .pricePerDay(350)
                .dayOfProduction(LocalDate.of(2010, 1, 1))
                .build();

        //When
        GetCarDto getCarDto = carMapper.mapToGetCarDto(car);

        //Then
        assertEquals(1L, getCarDto.getId().longValue());
        assertEquals("Premium", getCarDto.getCarClass());
        assertEquals("Sedan", getCarDto.getTypeOfCar());
        assertEquals("Ford", getCarDto.getProducer());
        assertEquals("Focus", getCarDto.getModel());
        assertEquals(true, getCarDto.isAvailability());
        assertEquals(5, getCarDto.getNumberOfSeats());
        assertEquals("white", getCarDto.getColor());
        assertEquals(350, getCarDto.getPricePerDay(), 0.1);
    }
}