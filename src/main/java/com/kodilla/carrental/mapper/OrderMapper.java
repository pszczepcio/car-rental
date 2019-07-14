package com.kodilla.carrental.mapper;

import com.kodilla.carrental.dao.CarDao;
import com.kodilla.carrental.dao.UserDao;
import com.kodilla.carrental.domain.Order;
import com.kodilla.carrental.dto.CreateOrderDto;
import com.kodilla.carrental.dto.OrderDto;
import com.kodilla.carrental.exception.CarNotFoundException;
import com.kodilla.carrental.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component

public class OrderMapper {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private UserDao userDao;

    @Autowired
    private CarDao carDao;

    public Order mapToOrder(final CreateOrderDto createOrderDto) throws UserNotFoundException, CarNotFoundException {
         return new Order(
                createOrderDto.getDateOfCarRental(),
                createOrderDto.getDateOfReturnCar(),
         userDao.findById(createOrderDto.getUserId()).orElseThrow(UserNotFoundException::new),
         carDao.findById(createOrderDto.getCarId()).orElseThrow(CarNotFoundException::new));
    }

    public List<OrderDto> mapToOrderDtoList(final List<Order> orderList) {
        return orderList.stream()
                .map(o -> new OrderDto(
                        o.getId(),
                        o.getOrderNumber(),
                        o.getDateOfOrder(),
                        o.getDateOfCarRental(),
                        o.getDateOfReturnCar(),
                        o.isStatusOrder(),
                        o.getPrize(),
                        o.getUser().getId(),
                        o.getCar().getId()))
                .collect(Collectors.toList());
    }

    public OrderDto mapToOrderDto(final Order order) {
        return new OrderDto(
                order.getId(),
                order.getOrderNumber(),
                order.getDateOfOrder(),
                order.getDateOfCarRental(),
                order.getDateOfReturnCar(),
                order.isStatusOrder(),
                order.getPrize(),
                order.getUser().getId(),
                order.getCar().getId()
        );
    }
}
