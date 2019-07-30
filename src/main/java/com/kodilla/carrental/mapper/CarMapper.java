package com.kodilla.carrental.mapper;
import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.dto.CarDto;
import com.kodilla.carrental.dto.CreateCarDto;
import com.kodilla.carrental.dto.GetCarDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarMapper {

    public Car mapToCar(final CreateCarDto createCarDto) {
        return new Car().builder()
                .carClass(createCarDto.getCarClass())
                 .typeOfCar(createCarDto.getTypeOfCar())
                 .producer(createCarDto.getProducer())
                 .model(createCarDto.getModel())
                 .dayOfProduction(createCarDto.getDayOfProduction())
                 .pricePerDay(createCarDto.getPricePerDay())
                 .color(createCarDto.getColor())
                 .numberOfSeats(createCarDto.getNumberOfSeats())
                 .availability(createCarDto.isAvailability())
                 .equipments(new ArrayList<>())
                 .build();
    }

    public List<CarDto> getCarsDtoList(final List<Car> carList) {
        return carList.stream()
                .map(c -> new CarDto(
                        c.getId(),
                        c.getCarClass(),
                        c.getTypeOfCar(),
                        c.getProducer(),
                        c.getModel(),
                        c.getDayOfProduction(),
                        c.getPricePerDay(),
                        c.getColor(),
                        c.getNumberOfSeats(),
                        c.isAvailability()))
                .collect(Collectors.toList());
    }

    public GetCarDto mapToGetCarDto(final Car car) {
        return new GetCarDto(
                car.getId(),
                car.getCarClass(),
                car.getTypeOfCar(),
                car.getProducer(),
                car.getModel(),
                car.getDayOfProduction(),
                car.getPricePerDay(),
                car.getColor(),
                car.getNumberOfSeats(),
                car.isAvailability(),
                car.getEquipments().stream()
                    .map(e -> e.getId())
                    .collect(Collectors.toList()));
    }
}


