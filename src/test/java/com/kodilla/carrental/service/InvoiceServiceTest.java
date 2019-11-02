package com.kodilla.carrental.service;

import com.kodilla.carrental.dao.InvoiceDao;
import com.kodilla.carrental.dao.OrderDao;
import com.kodilla.carrental.dao.UserDao;
import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.domain.Invoice;
import com.kodilla.carrental.domain.Order;
import com.kodilla.carrental.domain.User;
import com.kodilla.carrental.exception.InvoiceNotFoundException;
import com.kodilla.carrental.exception.OrderNotFoundException;
import com.kodilla.carrental.exception.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceServiceTest {

    private User user;
    private Car car;
    private Order order;
    private Order secondOrder;

    @InjectMocks
    private InvoiceService invoiceService;

    @Mock
    private UserDao userDao;

    @Mock
    private OrderDao orderDao;

    @Mock
    private InvoiceDao invoiceDao;

    @Before
    public void init() {
       user = new User(
                1L,
                "name",
                "surname",
                123456789,
                "user@email.com",
                "password",
                false,
                new ArrayList<>(),
                new ArrayList<>()
        );

       car = new Car().builder()
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

        order = new Order(
                1L,
                "Order/10.10.2019/1",
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                false,
                500,
                "cb",
                user,
                car
        );

        secondOrder = new Order(
                2L,
                "Order/15.10.2019/2",
                LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(10),
                false,
                500,
                "cb",
                user,
                car);
    }

    @Test
    public void saveInvoice() throws UserNotFoundException, OrderNotFoundException {
        //Given
        Invoice invoiceAfterSave = new Invoice(1L, "Invoice/10.10.2019/1", user, order);
        //When
        Invoice invoice = new Invoice(user, order);
        when(userDao.findById(1L)).thenReturn(ofNullable(user));
        when(userDao.save(user)).thenReturn(user);
        when(orderDao.findById(1L)).thenReturn(ofNullable(order));
        when(invoiceDao.save(invoice)).thenReturn(invoiceAfterSave);
        when(invoiceService.saveInvoice(invoice)).thenReturn(invoiceAfterSave);
        Invoice newInvoice = invoiceService.saveInvoice(invoice);

        //Then
        assertEquals(1L, newInvoice.getId().longValue());
        assertEquals(123456789, newInvoice.getUser().getPhone());
        assertEquals("Invoice/10.10.2019/1", newInvoice.getInvoiceNumber());
        assertEquals("name", newInvoice.getUser().getName());
        assertEquals("Order/10.10.2019/1", newInvoice.getOrder().getOrderNumber());
        assertEquals("Sedan", newInvoice.getOrder().getCar().getTypeOfCar());
    }

    @Test
    public void getInvoice() {
        //Given
        Long id = 1L;
        Invoice invoice = new Invoice(1L, "Invoice/10.10.2019/1", user, order);
        //When
       when(invoiceDao.findById(id)).thenReturn(of(invoice));
       Optional<Invoice> getInvoice = invoiceService.getInvoice(id);
       //Then
        assertEquals(1L, getInvoice.get().getId().longValue());
        assertEquals("Invoice/10.10.2019/1", getInvoice.get().getInvoiceNumber());
        assertEquals("user@email.com", getInvoice.get().getUser().getEmail());
        assertEquals("Order/10.10.2019/1", getInvoice.get().getOrder().getOrderNumber());
    }

    @Test
    public void shouldDeleteInvoice() {
        //Given && When && Then
        Long id = 1L;
        invoiceService.deleteInvoice(id);
        verify(invoiceDao, times(1)).deleteById(id);
    }

    @Test
    public void shouldGetInvoiceList() {
        //Given
        List<Invoice> invoiceList = new ArrayList<>();
        Invoice invoice1 = new Invoice(1L, "Invoice/10.10.2019/1", user, order);
        Invoice invoice2 = new Invoice(2L, "Invoice/15.10.2019/2", user, secondOrder);
        invoiceList.add(invoice1);
        invoiceList.add(invoice2);

        //When
        when(invoiceDao.findAll()).thenReturn(invoiceList);
        List<Invoice> invoices = invoiceService.getInvoiceList();

        //Then
        assertEquals(2, invoices.size());
        assertEquals("Invoice/10.10.2019/1", invoices.get(0).getInvoiceNumber());
        assertEquals("Invoice/15.10.2019/2", invoices.get(1).getInvoiceNumber());
    }

}