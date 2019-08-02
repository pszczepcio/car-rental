package com.kodilla.carrental.service;

import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.domain.Invoice;
import com.kodilla.carrental.domain.Order;
import com.kodilla.carrental.domain.User;
import com.kodilla.carrental.exception.InvoiceNotFoundException;
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
public class InvoiceServiceTest {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Test
    public void saveInvoice() throws UserNotFoundException, OrderNotFoundException, InvoiceNotFoundException {
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

        Invoice invoice = new Invoice(user, order);
        userService.saveUser(user);
        carService.saveCar(car);
        orderService.saveOrder(order);
        String orderNumber = orderService.getOrder(order.getId()).orElseThrow(OrderNotFoundException::new).getOrderNumber();
        String invoiceNumber = orderNumber.replace("Order", "Invoice");

        //When
        invoiceService.saveInvoice(invoice);
        Long invoiceId = invoiceService.getInvoice(invoice.getId()).orElseThrow(InvoiceNotFoundException::new).getId();
        Invoice invoiceAfterSave = invoiceService.getInvoice(invoiceId).orElseThrow(InvoiceNotFoundException::new);

        //Then
        assertNotNull(invoiceId);
        assertEquals(invoiceNumber, invoiceAfterSave.getInvoiceNumber());
        assertEquals("name", invoiceAfterSave.getUser().getName());
        assertEquals(orderNumber, invoiceAfterSave.getOrder().getOrderNumber());
        assertEquals("Sedan", invoiceAfterSave.getOrder().getCar().getTypeOfCar());

        //Cleanup
        orderService.deleteOrder(order.getId());
        invoiceService.deleteInvoice(invoiceId);

        carService.deleteCar(car.getId());
        userService.deleteUser(user.getId());
    }

    @Test
    public void getInvoiceList() throws UserNotFoundException, OrderNotFoundException {
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
                "user1",
                "surname1",
                123456789,
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
                .color("Red")
                .pricePerDay(250)
                .dayOfProduction(LocalDate.of(2005,1,1))
                .build();

        Order order = new Order(
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                user,
                car
        );

        Order order1 = new Order(
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(15),
                user1,
                car1
        );

        userService.saveUser(user);
        userService.saveUser(user1);
        carService.saveCar(car);
        carService.saveCar(car1);
        orderService.saveOrder(order);
        orderService.saveOrder(order1);
        Invoice invoice = new Invoice(user, order);
        Invoice invoice1 = new Invoice(user1, order1);

        //When
        invoiceService.saveInvoice(invoice);
        invoiceService.saveInvoice(invoice1);
        List<Invoice> invoiceList = invoiceService.getInvoiceList();

        //Then
        assertEquals(2, invoiceList.size());
        assertEquals("Fiesta", invoiceList.get(1).getOrder().getCar().getModel());

        //Cleanup
        orderService.deleteOrder(order1.getId());
        orderService.deleteOrder(order.getId());

        invoiceService.deleteInvoice(invoice1.getId());
        invoiceService.deleteInvoice(invoice.getId());

        carService.deleteCar(car1.getId());
        carService.deleteCar(car.getId());

        userService.deleteUser(user1.getId());
        userService.deleteUser(user.getId());
    }

    @Test
    public void getInvoice() {
    }

    @Test
    public void deleteInvoice() {
    }
}