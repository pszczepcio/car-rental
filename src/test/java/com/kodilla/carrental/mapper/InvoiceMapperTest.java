package com.kodilla.carrental.mapper;

import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.domain.Invoice;
import com.kodilla.carrental.domain.Order;
import com.kodilla.carrental.domain.User;
import com.kodilla.carrental.dto.CreateCarDto;
import com.kodilla.carrental.dto.CreateInvoiceDto;
import com.kodilla.carrental.dto.GetInvoiceDto;
import com.kodilla.carrental.dto.InvoiceDto;
import com.kodilla.carrental.exception.OrderNotFoundException;
import com.kodilla.carrental.exception.UserNotFoundException;
import com.kodilla.carrental.service.CarService;
import com.kodilla.carrental.service.InvoiceService;
import com.kodilla.carrental.service.OrderService;
import com.kodilla.carrental.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class InvoiceMapperTest {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Test
    public void mapToInvoice() throws UserNotFoundException, OrderNotFoundException {
        //Given
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

        User user = new User(
                "name",
                "surname",
                123456789,
                "email@email.com",
                "password"
        );

        Order order = new Order(
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(15),
                user,
                car
        );

        carService.saveCar(car);
        userService.saveUser(user);
        orderService.saveOrder(order);

        CreateInvoiceDto createInvoiceDto = new CreateInvoiceDto(
                user.getId(),
                order.getId()
        );
        //When
        Invoice invoice = invoiceMapper.mapToInvoice(createInvoiceDto);

        //Then
        assertEquals("name", invoice.getUser().getName());
        assertEquals("surname", invoice.getUser().getSurname());
        assertEquals(123456789, invoice.getUser().getPhone());
        assertEquals("email@email.com", invoice.getUser().getEmail());
        assertEquals("password", invoice.getUser().getPassword());
        assertEquals("Premium", invoice.getOrder().getCar().getCarClass());
        assertEquals(LocalDate.now().plusDays(10), invoice.getOrder().getDateOfCarRental());
        assertEquals(1L, invoice.getOrder().getId().longValue());

        //Cleanup

        orderService.deleteOrder(order.getId());
        userService.deleteUser(user.getId());
        carService.deleteCar(car.getId());
    }

    @Test
    public void getInvoiceDtoList() {
        //Given
        List<Order> orderList = new ArrayList<>();
        List<Invoice> invoiceList = new ArrayList<>();
        List<Order> orderList1 = new ArrayList<>();
        List<Invoice> invoiceList1 = new ArrayList<>();
        User user = new User(
                1L,
                "name",
                "surname",
                123456789,
                "email@email.com",
                "password",
                false,
                orderList,
                invoiceList
        );

        User user1 = new User(
                2L,
                "name1",
                "surname1",
                987654321,
                "email1@email.com",
                "password1",
                false,
                orderList1,
                invoiceList1
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
                .typeOfCar("Kombi")
                .producer("Skoda")
                .model("Octavia")
                .availability(true)
                .numberOfSeats(5)
                .color("white")
                .pricePerDay(250)
                .dayOfProduction(LocalDate.of(2005,1,1))
                .build();

        Order order = new Order(
                1L,
                "orderNumber",
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(15),
                false,
                400.0,
                "cb-radio",
                user,
                car
        );

        Order order1 = new Order(
                2L,
                "orderNumber1",
                LocalDate.now(),
                LocalDate.now().plusDays(16),
                LocalDate.now().plusDays(20),
                false,
                300.0,
                "towbar",
                user1,
                car1
        );

        user.getOrderList().add(order);
        user1.getOrderList().add(order1);

        Invoice invoice = new Invoice(
                1L,
                "invoiceNumber",
                user,
                order
        );

        Invoice invoice1 = new Invoice(
                2L,
                "invoiceNumber1",
                user1,
                order1
        );

        user.getInvoiceList().add(invoice);
        user1.getInvoiceList().add(invoice1);

        List<Invoice> invoiceList2 = new ArrayList<>();
        invoiceList2.add(invoice);
        invoiceList2.add(invoice1);

        //When
        List<GetInvoiceDto> getInvoiceDto = invoiceMapper.getInvoiceDtoList(invoiceList2);
        Long invoiceOneId = getInvoiceDto.get(0).getId();
        Long invoiceTwoId = getInvoiceDto.get(1).getId();
        String invoiceNumberOne = getInvoiceDto.get(0).getInvoiceNumber();
        String invoiceNumberTwo = getInvoiceDto.get(1).getInvoiceNumber();

        //Then
        assertEquals(2, getInvoiceDto.size());
        assertEquals(java.util.Optional.ofNullable(1L), java.util.Optional.ofNullable(invoiceOneId));
        assertEquals(java.util.Optional.ofNullable(2L), java.util.Optional.ofNullable(invoiceTwoId));
        assertEquals("invoiceNumber", invoiceNumberOne);
        assertEquals("invoiceNumber1", invoiceNumberTwo);
    }

    @Test
    public void mapToInvoiceDto() {
        //Given
        List<Order> orderList = new ArrayList<>();
        List<Invoice> invoiceList = new ArrayList<>();

        User user = new User(
                1L,
                "name",
                "surname",
                123456789,
                "email@email.com",
                "password",
                false,
                orderList,
                invoiceList
        );

        Order order = new Order(
                1L,
                "orderNumber",
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(15),
                false,
                400.0,
                "cb-radio",
                user,
                new Car()
        );

        Invoice invoice = new Invoice(
                1L,
                "invoice/number/1",
                user,
                order
        );

        //When
        InvoiceDto invoiceDto = invoiceMapper.mapToInvoiceDto(invoice);

        //Then
        assertEquals(1L, invoiceDto.getId().longValue());
        assertEquals("invoice/number/1", invoiceDto.getInvoiceNumber());
        assertEquals(1L, invoiceDto.getUserId().longValue());
        assertEquals(1L, invoiceDto.getOrderId().longValue());
    }
}