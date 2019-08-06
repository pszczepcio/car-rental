package com.kodilla.carrental.mapper;

import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.domain.Invoice;
import com.kodilla.carrental.domain.Order;
import com.kodilla.carrental.domain.User;
import com.kodilla.carrental.dto.CreateUserDto;
import com.kodilla.carrental.dto.UserDto;
import com.kodilla.carrental.dto.UserDtoList;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void mapToUser() {
    //Given
        CreateUserDto createUserDto = new CreateUserDto(
                "Piotrek",
                "Kowalski",
                123456789,
                "piotrek@eamil.com",
                "password"
        );

        //When
        User user = userMapper.mapToUser(createUserDto);

        //Then
        assertEquals("Piotrek", user.getName());
        assertEquals("Kowalski", user.getSurname());
        assertEquals(123456789, user.getPhone());
        assertEquals("piotrek@eamil.com", user.getEmail());
        assertEquals("password", user.getPassword());
    }

    @Test
    public void getUsersDtoList() {
        //Given
        User user = new User(
                1L,
                "Piotrek",
                "Kowalski",
                111111111,
                "piotrek@eamil.com",
                "password",
                false,
                new ArrayList<>(),
                new ArrayList<>()
        );

        User user1 = new User(
                2L,
                "Marcin",
                "Kopacz",
                222222222,
                "marcin@eamil.com",
                "password1",
                false,
                new ArrayList<>(),
                new ArrayList<>()
        );

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);

        //When
        List<UserDtoList> userDtoList = userMapper.getUsersDtoList(userList);
        UserDtoList userDtoList1 = userDtoList.get(0);
        UserDtoList userDtoList2 = userDtoList.get(1);

        //Then
        assertEquals(2, userDtoList.size());
        assertEquals(1L, userDtoList1.getId().longValue());
        assertEquals(2L, userDtoList2.getId().longValue());
        assertEquals("Piotrek", userDtoList1.getName());
        assertEquals("Marcin", userDtoList2.getName());
        assertEquals("Kowalski", userDtoList1.getSurname());
        assertEquals("Kopacz", userDtoList2.getSurname());
        assertEquals(111111111, userDtoList1.getPhone());
        assertEquals(222222222, userDtoList2.getPhone());
        assertEquals("piotrek@eamil.com", userDtoList1.getEmail());
        assertEquals("marcin@eamil.com", userDtoList2.getEmail());
        assertEquals(false, userDtoList1.isLoginStatus());
        assertEquals(false, userDtoList2.isLoginStatus());
    }

    @Test
    public void mapToUserDto() {
        //Given
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

        User user = new User(
                1L,
                "Piotrek",
                "Kowalski",
                111111111,
                "piotrek@eamil.com",
                "password",
                false,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Order order = new Order(
          1L,
          "order_number",
          LocalDate.now(),
          LocalDate.now().plusDays(5),
          LocalDate.now().plusDays(10),
                false,
                550.0,
                "cb-radio",
                user,
                car
        );

        Invoice invoice = new Invoice(
          1L,
          "innvoice_number",
          user,
          order
        );

        user.getOrderList().add(order);
        user.getInvoiceList().add(invoice);

        //When
        UserDto userDto = userMapper.mapToUserDto(user);

        //Then
        assertEquals(1L, userDto.getId().longValue());
        assertEquals(111111111, userDto.getPhone());
        assertEquals(false, userDto.isLoginStatus());
        assertEquals("Piotrek", userDto.getName());
        assertEquals("piotrek@eamil.com", userDto.getEmail());
    }
}
