package com.kodilla.carrental.mapper;

import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.domain.Invoice;
import com.kodilla.carrental.domain.Order;
import com.kodilla.carrental.domain.User;
import com.kodilla.carrental.dto.CreateOrderDto;
import com.kodilla.carrental.dto.OrderDto;
import com.kodilla.carrental.exception.CarNotFoundException;
import com.kodilla.carrental.exception.UserNotFoundException;
import com.kodilla.carrental.service.CarService;
import com.kodilla.carrental.service.OrderService;
import com.kodilla.carrental.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CarService carService;

    @Test
    public void mapToOrder() throws CarNotFoundException, UserNotFoundException {
    //Given
        User user = new User(
                "name",
                "surname",
                123456789,
                "email@email.com",
                "password"
        );

        Car car = new Car().builder()
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

        userService.saveUser(user);
        carService.saveCar(car);

        CreateOrderDto createOrderDto = new CreateOrderDto(
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                user.getId(),
                car.getId(),
                "Cb-radio"
        );

        //When
        Order order = orderMapper.mapToOrder(createOrderDto);

        //Then
        assertEquals(LocalDate.now(), order.getDateOfCarRental());
        assertEquals(LocalDate.now().plusDays(3), order.getDateOfReturnCar());
        assertEquals("email@email.com", order.getUser().getEmail());
        assertEquals("Ford", order.getCar().getProducer());

        //Cleanup
        userService.deleteUser(user.getId());
        carService.deleteCar(car.getId());
    }

    @Test
    public void mapToOrderDtoList() {
        //Given
        User user = new User(
                1L,
                "name",
                "surname",
                123456789,
                "email@email.com",
                "password",
                false,
                new ArrayList<>(),
                new ArrayList<>()
        );

        User user1 = new User(
                2L,
                "name1",
                "surname1",
                987654321,
                "email1@email.com",
                "password1",
                false,
                new ArrayList<>(),
                new ArrayList<>()
        );

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

        Order orderOne = new Order(
                1L,
                "orderNumber1",
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(15),
                false,
                400.0,
                "cb-radio",
                user,
                car
        );

        Order orderTwo = new Order(
                2L,
                "orderNumber2",
                LocalDate.now(),
                LocalDate.now().plusDays(16),
                LocalDate.now().plusDays(20),
                false,
                350.0,
                "cb-radio",
                user1,
                car1
        );

        List<Order> orderList = new ArrayList<>();
        orderList.add(orderOne);
        orderList.add(orderTwo);

        //When
        List<OrderDto> orderDtoList = orderMapper.mapToOrderDtoList(orderList);
        OrderDto one = orderDtoList.get(0);
        OrderDto two = orderDtoList.get(1);

        //Then
        assertEquals(2, orderDtoList.size());
        assertEquals(1L, one.getId().longValue());
        assertEquals(2L, two.getId().longValue());
        assertEquals("orderNumber1", one.getOrderNumber());
        assertEquals("orderNumber2", two.getOrderNumber());
    }

    @Test
    public void mapToOrderDto() {
        //Given
        User user = new User(
                1L,
                "name",
                "surname",
                123456789,
                "email@email.com",
                "password",
                false,
                new ArrayList<>(),
                new ArrayList<>()
        );

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

        Order orderOne = new Order(
                1L,
                "orderNumber1",
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(15),
                false,
                400.0,
                "cb-radio",
                user,
                car
        );

        //When
        OrderDto orderDto = orderMapper.mapToOrderDto(orderOne);

        //Then
        assertEquals(1L, orderDto.getId().longValue());
        assertEquals("orderNumber1", orderDto.getOrderNumber());
        assertEquals(LocalDate.now(), orderDto.getDateOfOrder());
        assertEquals(LocalDate.now().plusDays(10), orderDto.getDateOfCarRental());
        assertEquals(LocalDate.now().plusDays(15), orderDto.getDateOfReturnCar());
        assertEquals(false, orderDto.isStatusOrder());
        assertEquals(400.0, orderDto.getPrize(), 0.1);
        assertEquals(1L, orderDto.getUserId().longValue());
        assertEquals(1L, orderDto.getCarId().longValue());
    }
}