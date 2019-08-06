package com.kodilla.carrental.scheduler;

import com.kodilla.carrental.WeatherConditions;
import com.kodilla.carrental.dao.OrderDao;
import com.kodilla.carrental.domain.*;
import com.kodilla.carrental.dto.UpdateCarAndEquipment;
import com.kodilla.carrental.exception.AdditionalEquipmentNotFoundException;
import com.kodilla.carrental.exception.CarNotFoundException;
import com.kodilla.carrental.openweather.OpenWeatherClient;
import com.kodilla.carrental.service.*;
import com.kodilla.carrental.weather.doimain.Days;
import com.kodilla.carrental.weather.doimain.ListDto;
import com.kodilla.carrental.weather.doimain.OpenWeatherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

@Component
@Transactional
public class CalendarScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalendarScheduler.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpleEmailService emailService;

    @Autowired
    private OpenWeatherClient openWeatherClient;
    private LocalDate today = LocalDate.now();
    private Days days = new Days();

    @Scheduled(cron = "0 0 22 * * *") //(fixedDelay = 1000)
    public void controlTheCarStatus() throws CarNotFoundException {
        LOGGER.info("The availability of cars has started");
        List<Order> orderList = orderDao.findAll();
        changeStatusToAvailable(orderList);
        changeStatusToUnavailable(orderList);
        LOGGER.info("The availability of cars has been finished");
    }

    @Scheduled(cron = "* 8 15 * * *")
    public void weatherPromotion() {
        LOGGER.info("The process of checking the weather has begun.");
        OpenWeatherDto openWeatherDto = openWeatherClient.getWeatherFor5Days();
        LOGGER.info("The process of checking the weather has finished.");
        if (calculationAverageTemperature(openWeatherDto) >= 25.0) {
            if (checkingTypeWeather(openWeatherDto)) {
                    sendCoupon();
            }
        }
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

    private double calculationAverageTemperature(final OpenWeatherDto openWeatherDto) {
        List<Double> temperature = openWeatherDto.getListDto().stream()
                .filter(t -> t.getDt_txt().equals(days.getToday()) || t.getDt_txt().equals(days.getNextDayOne())
                        || t.getDt_txt().equals(days.getNextDayTwo()) || t.getDt_txt().equals(days.getNextDayThree())
                        || t.getDt_txt().equals(days.getNextDayFour()))
                .map(t -> Double.parseDouble(t.getMain().getTemp()))
                .collect(Collectors.toList());
        OptionalDouble averageTemperature = IntStream.range(0 , temperature.size())
                .mapToDouble(t -> temperature.get(t).doubleValue())
                .average();
        return averageTemperature.getAsDouble();
    }

    private boolean checkingTypeWeather(final OpenWeatherDto openWeatherDto){
        List<String> kindOfWeather = openWeatherDto.getListDto().stream()
                    .filter(t -> t.getDt_txt().equals(days.getToday()) || t.getDt_txt().equals(days.getNextDayOne())
                            || t.getDt_txt().equals(days.getNextDayTwo()) || t.getDt_txt().equals(days.getNextDayThree())
                            || t.getDt_txt().equals(days.getNextDayFour()))
                    .filter(t -> t.getWeatherDto().get(0).getMain().equals(WeatherConditions.Clear))
                    .map(t -> t.getWeatherDto().get(0).getMain())
                    .collect(Collectors.toList());
        if (kindOfWeather.size() == 5){
            return true;
        }
        return false;
    }

    private void sendCoupon(){
        LOGGER.info("The sending of discount offers on cabriolets has begun");
        List<User> userList = userService.getUsersList();
        List<String> emailList = userList.stream()
                .map(user -> user.getEmail())
                .collect(Collectors.toList());
        for (int i = 0 ; i < emailList.size() ; i++){
            emailService.send(new Mail(
                    emailList.get(i),
                    "Promotion for cabriolet in Car Rental",
                    "In the days from " + days.getToday().replace("15:00:00", "") + "to "
                            + days.getNextDayFour().replace("15:00:00", "")
                            + "the car rental company, there is a 50% discount on cabriolet cars. " +
                            "Save this email and show it at checkout. " +
                            "\n\nBest regards,\nCar Rentals Team"));
        }
        LOGGER.info("The sending of discount offers on cabriolets has been completed.");
    }
}
