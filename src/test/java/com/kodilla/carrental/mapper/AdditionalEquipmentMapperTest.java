package com.kodilla.carrental.mapper;

import com.kodilla.carrental.domain.AdditionalEquipment;
import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.dto.CreateEquipmentDto;
import com.kodilla.carrental.dto.EquipmentDto;
import com.kodilla.carrental.dto.GetEquipmentDto;
import com.kodilla.carrental.service.AdditionalEquipmentService;
import com.kodilla.carrental.service.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AdditionalEquipmentMapperTest {

    @Autowired
    AdditionalEquipmentMapper additionalEquipmentMapper;

    @Autowired
    private AdditionalEquipmentService additionalEquipmentService;

    @Autowired
    private CarService carService;

    @Test
    public void mapToAdditionalEquipment() {
        //Given
        CreateEquipmentDto equipmentDto = new CreateEquipmentDto(
                "Cb-radio",
                20.0);

        //When
        AdditionalEquipment additionalEquipment = additionalEquipmentMapper.mapToAdditionalEquipment(equipmentDto);

        //Then
        assertEquals("Cb-radio", additionalEquipment.getEquipment());
        assertEquals(20.0, additionalEquipment.getPrize(),  0.1);
    }

    @Test
    public void mapToEquipmentDtoList() {
        //Given
        AdditionalEquipment equipment = new AdditionalEquipment(
                "cb-radio",
                20.0);

        AdditionalEquipment equipment1 = new AdditionalEquipment(
                "towbar",
                30.0);

        List<AdditionalEquipment> additionalEquipmentList = new ArrayList<>();
        additionalEquipmentList.add(equipment);
        additionalEquipmentList.add(equipment1);

        //When
        List<GetEquipmentDto> getEquipmentDtoList = additionalEquipmentMapper.mapToEquipmentDtoList(additionalEquipmentList);
        int getEquipmentDtoListSize = getEquipmentDtoList.size();

        //Then
        assertEquals(2, getEquipmentDtoListSize);
        assertEquals("cb-radio", getEquipmentDtoList.get(0).getEquipment());
        assertEquals("towbar", getEquipmentDtoList.get(1).getEquipment());

    }

    @Test
    public void mapToEquipmentDto() {
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
                .dayOfProduction(LocalDate.of(2010,1,1))
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
                .dayOfProduction(LocalDate.of(2015,2,10))
                .build();

        List<Car> carList = new ArrayList<>();
        carList.add(car);
        carList.add(car1);

        AdditionalEquipment additionalEquipment = new AdditionalEquipment(
                1L,
                "railings",
                40.0,
                carList);

        //When
        EquipmentDto equipmentDto = additionalEquipmentMapper.mapToEquipmentDto(additionalEquipment);

        //Then
        assertEquals(1L, equipmentDto.getId().longValue());
        assertEquals(1L, equipmentDto.getCarIdList().get(0).longValue());
        assertEquals(2L, equipmentDto.getCarIdList().get(1).longValue());
        assertEquals("railings", equipmentDto.getEquipment());
        assertEquals(40.0, equipmentDto.getPrize(), 0.1);
    }
}