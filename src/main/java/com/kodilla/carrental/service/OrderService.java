package com.kodilla.carrental.service;

import com.kodilla.carrental.dao.CarDao;
import com.kodilla.carrental.dao.OrderDao;
import com.kodilla.carrental.dao.UserDao;
import com.kodilla.carrental.domain.Order;
import com.kodilla.carrental.domain.User;
import com.kodilla.carrental.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CarDao carDao;

    @Autowired
    private UserDao userDao;

    public Order saveOrder(final Order order) throws UserNotFoundException {
        User user = userDao.findById(order.getUser().getId()).orElseThrow(UserNotFoundException::new);
        user.getOrderList().add(order);
        Order newOrder = orderDao.save(order);
        fillingOrder(newOrder);
        return orderDao.save(newOrder);
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
        double prizePerDay = carDao.findById(order.getCar().getId()).get().getPricePerDay();
        LocalDate rentalDate = order.getDateOfCarRental();
        LocalDate returnDate = order.getDateOfReturnCar();
        double differenceOfDays = ChronoUnit.DAYS.between(rentalDate, returnDate);
        double prize = prizePerDay * differenceOfDays;
        String orderNumber = "Order/" + LocalDate.now().toString() +"/" + order.getId();
        order.setOrderNumber(orderNumber);
        order.setPrize(prize);
        orderDao.save(order);
    }
}
