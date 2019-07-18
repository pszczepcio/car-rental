package com.kodilla.carrental.service;

import com.kodilla.carrental.dao.OrderDao;
import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.domain.Mail;
import com.kodilla.carrental.domain.Order;
import com.kodilla.carrental.domain.User;
import com.kodilla.carrental.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserService userService;

    @Autowired
    private CarService carService;

    @Autowired
    private SimpleEmailService emailService;

    public Order saveOrder(final Order order) throws UserNotFoundException {
        Order newOrder = new Order();
        if(checkingAvailabilityCar(order)) {
            User user = userService.getUserDto(order.getUser().getId());
            user.getOrderList().add(order);
            newOrder = orderDao.save(order);
            fillingOrder(newOrder);
        }
        if (newOrder.getId() != null) {
            emailService.send(new Mail(
                    order.getUser().getEamil(),
                    "New Order in Car Rental Company ",
                    "You placed an order at a car rental company. Order number is: " + order.getOrderNumber() +
                            "\n\nBest regards,\nCar Rentals Team"
            ));
            return orderDao.save(newOrder);
        }
        return new Order();
    }

    public List<Order> getOrders() {
        return orderDao.findAll();
    }

    public Optional<Order> getOrder(Long id) {
        return orderDao.findById(id);
    }

    public void deleteOrder(Long id) {
        orderDao.deleteById(id);
    }

    private void fillingOrder (Order order) {
        double prizePerDay = carService.getCar(order.getCar().getId()).get().getPricePerDay();
        LocalDate rentalDate = order.getDateOfCarRental();
        LocalDate returnDate = order.getDateOfReturnCar();
        double differenceOfDays = ChronoUnit.DAYS.between(rentalDate, returnDate);
        double prize = prizePerDay * differenceOfDays;
        String orderNumber = "Order/" + LocalDate.now().toString() +"/" + order.getId();
        order.setOrderNumber(orderNumber);
        order.setPrize(prize);
        orderDao.save(order);
    }

    private boolean checkingAvailabilityCar(final Order newOrder) {
        List<Order> orderList = orderDao.findAll();
        LocalDate carRental = newOrder.getDateOfCarRental();
        LocalDate carReturn = newOrder.getDateOfReturnCar();
        List<Car> availableCars = orderList.stream()
                .filter(order ->
                        ((carRental.isBefore(order.getDateOfCarRental()) && carReturn.isBefore(order.getDateOfCarRental())
                                && newOrder.getCar().isAvailability()) ||
                          (carRental.isAfter(order.getDateOfReturnCar()) && carReturn.isAfter(order.getDateOfReturnCar())
                                  && newOrder.getCar().isAvailability())))
                .map(order -> order.getCar())
                .collect(Collectors.toList());
        if(availableCars.size() != 0) {
            return true;
        }
        return false;
    }
}
