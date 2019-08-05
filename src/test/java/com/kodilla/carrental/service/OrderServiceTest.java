package com.kodilla.carrental.service;

import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.domain.Order;
import com.kodilla.carrental.domain.User;
import com.kodilla.carrental.exception.OrderNotFoundException;
import com.kodilla.carrental.exception.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CarService carService;

    @Autowired
    private OrderService orderService;

    @Test
    public void saveOrder() throws UserNotFoundException, OrderNotFoundException {
        //Given
        User user = new User(
                "name",
                "surname",
                123456789,
                "email@email.com",
                "password",
                false
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

        Order order = new Order(
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                user,
                car
        );

        userService.saveUser(user);
        carService.saveCar(car);

        //When
        orderService.saveOrder(order);
        Order orderAfterSave = orderService.getOrder(order.getId()).orElseThrow(OrderNotFoundException::new);
        //Then
        assertNotNull(order.getId());
        assertEquals(LocalDate.now(), orderAfterSave.getDateOfCarRental());
        assertEquals(LocalDate.now().plusDays(5), orderAfterSave.getDateOfReturnCar());
        assertEquals("name", orderAfterSave.getUser().getName());
        assertEquals("Focus", orderAfterSave.getCar().getModel());

        //Cleanup
        orderService.deleteOrder(order.getId());
        userService.deleteUser(user.getId());
        carService.deleteCar(car.getId());
    }

    @Test
    public void getOrders() throws UserNotFoundException, OrderNotFoundException {
        //Given
        User user = new User(
                "name",
                "surname",
                123456789,
                "user@email.com",
                "password",
                false
        );

        User user1 = new User(
                "name1",
                "surname1",
                987654321,
                "user1@email.com",
                "password1",
                false
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

        Car car1 = new Car().builder()
                .carClass("Basic")
                .typeOfCar("Hatchback")
                .producer("Ford")
                .model("Fiesta")
                .availability(true)
                .numberOfSeats(5)
                .color("grey")
                .pricePerDay(250)
                .dayOfProduction(LocalDate.of(2012,2,24))
                .build();

        Order order1 = new Order(
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                user,
                car
        );
        Order order2 = new Order(
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                user1,
                car1
        );
        userService.saveUser(user);
        userService.saveUser(user1);
        carService.saveCar(car);
        carService.saveCar(car1);

        //When
        orderService.saveOrder(order1);
        orderService.saveOrder(order2);
        List<Order> orderList = orderService.getOrders();

        System.out.println(order1.getId());
        System.out.println(order2.getId());

        //Then
        assertNotNull(order1.getId());
        assertNotNull(order2.getId());
        assertEquals(2, orderList.size());

        //Cleanup
        orderService.deleteOrder(order1.getId());
        orderService.deleteOrder(order2.getId());
        userService.deleteUser(user.getId());
        userService.deleteUser(user1.getId());
        carService.deleteCar(car.getId());
        carService.deleteCar(car1.getId());
    }

    @Test
    public void saveUpdateStatusOrderTest() throws UserNotFoundException, OrderNotFoundException {
        //Given
        User user = new User(
                "x",
                "y",
                123456789,
                "email@email.com",
                "password",
                false
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

        Order order = new Order(
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                user,
                car
        );

        userService.saveUser(user);
        carService.saveCar(car);
        orderService.saveOrder(order);

        //When
        boolean statusBeforeChange = orderService.getOrder(order.getId()).orElseThrow(OrderNotFoundException::new).isStatusOrder();
        order.setStatusOrder(true);
        orderService.saveUpdateStatusOrder(order);
        boolean statusAfterChange = orderService.getOrder(order.getId()).orElseThrow(OrderNotFoundException::new).isStatusOrder();
        //Then
        assertEquals(false, statusBeforeChange);
        assertEquals(true, statusAfterChange);

        //Cleanup
        orderService.deleteOrder(order.getId());
        userService.deleteUser(user.getId());
        carService.deleteCar(car.getId());
    }

    @Test
    public void orderWithAnInaccessibleCarTest() throws UserNotFoundException, OrderNotFoundException {
        //Given
        User user = new User(
                "name",
                "surname",
                123456789,
                "user@email.com",
                "password",
                false
        );

        Car car = new Car().builder()
                .carClass("Premium")
                .typeOfCar("Sedan")
                .producer("Ford")
                .model("Focus")
                .availability(false)
                .numberOfSeats(5)
                .color("white")
                .pricePerDay(350)
                .dayOfProduction(LocalDate.of(2010,1,1))
                .build();

        Order order = new Order(
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                user,
                car
        );

        carService.saveCar(car);
        userService.saveUser(user);

        //When
        orderService.saveOrder(order);

        //Then
        assertNull(order.getId());

        //Cleanup
        userService.deleteUser(user.getId());
        carService.deleteCar(car.getId());

    }


}