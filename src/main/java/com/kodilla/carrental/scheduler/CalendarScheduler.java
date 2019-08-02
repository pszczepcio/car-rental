package com.kodilla.carrental.scheduler;

import com.kodilla.carrental.dao.OrderDao;
import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.domain.Order;
import com.kodilla.carrental.exception.CarNotFoundException;
import com.kodilla.carrental.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CalendarScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalendarScheduler.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CarService carService;

    private LocalDate today = LocalDate.now();

    @Scheduled(cron = "0 0 22 * * *") //(fixedDelay = 1000)
    public void controlTheCarStatus() throws CarNotFoundException {
        LOGGER.info("The availability of cars has started");
        List<Order> orderList = orderDao.findAll();
        changeStatusToAvailable(orderList);
        changeStatusToUnavailable(orderList);
        LOGGER.info("The availability of cars has been finished");
    }

    private void changeStatusToUnavailable(final List<Order> orderList) throws CarNotFoundException {
        List<Car> carNotAvailableList = orderList.stream()
                .filter(order -> (today.isEqual(order.getDateOfCarRental()) || today.isAfter(order.getDateOfCarRental()))
                        && (today.isEqual(order.getDateOfReturnCar()) || today.isBefore(order.getDateOfReturnCar())))
                .map(order -> order.getCar())
                .collect(Collectors.toList());
        for (int i = 0 ; i < carNotAvailableList.size() ; i++) {
            Car car = carService.getCar(carNotAvailableList.get(i).getId()).orElseThrow(CarNotFoundException::new);
            car.setAvailability(false);
            carService.saveCar(car);
        }
    }

    private void changeStatusToAvailable(final List<Order> orderList) throws CarNotFoundException {
        List<Car> carAvailableList = orderList.stream()
                .filter(order -> (today.isBefore(order.getDateOfCarRental()) || today.isAfter(order.getDateOfReturnCar())))
                .map(order -> order.getCar())
                .collect(Collectors.toList());
        for (int i = 0 ; i <carAvailableList.size() ; i++) {
            Car car = carService.getCar(carAvailableList.get(i).getId()).orElseThrow(CarNotFoundException::new);
            car.setAvailability(true);
            carService.saveCar(car);
        }
    }
}
