package com.kodilla.carrental.mapper;

import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.dto.CarDto;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarMapper {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Car mapToCar(final CarDto carDto) {
        return new Car(
                carDto.getCarClass(),
                carDto.getTypeOfCar(),
                carDto.getProducer(),
                carDto.getModel(),
                carDto.getDayOfProduction(),
                carDto.getEquipment(),
                carDto.getPricePerDay(),
                carDto.getColor(),
                carDto.getNumberOfSeats(),
                carDto.isAvailability());
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
                        c.getEquipment(),
                        c.getPricePerDay(),
                        c.getColor(),
                        c.getNumberOfSeats(),
                        c.isAvailability()))
                .collect(Collectors.toList());
    }

    public CarDto mapToCarDto(final Car car) {
        return new CarDto(
                car.getId(),
                car.getCarClass(),
                car.getTypeOfCar(),
                car.getProducer(),
                car.getModel(),
                car.getDayOfProduction(),
                car.getEquipment(),
                car.getPricePerDay(),
                car.getColor(),
                car.getNumberOfSeats(),
                car.isAvailability());
    }
}


