package com.kodilla.carrental.controller;

import com.google.gson.*;
import com.kodilla.carrental.domain.Order;
import com.kodilla.carrental.domain.User;
import com.kodilla.carrental.dto.*;
import com.kodilla.carrental.localdateadapter.LocalDateAdapter;
import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.mapper.OrderMapper;
import com.kodilla.carrental.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderMapper orderMapper;

    @Test
    public void shouldGetOrders() throws Exception {
        //Given
        List<OrderDto> orderDtoList = new ArrayList<>();
        orderDtoList.add(new OrderDto(
                1L, "order/number/1", LocalDate.now(),
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(5),
                false, 400.0, 1L, 1L, "cb-radio"
        ));

        when(orderMapper.mapToOrderDtoList(orderService.getOrders())).thenReturn(orderDtoList);

        //When & then
        mockMvc.perform(get("/v1/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].orderNumber", is("order/number/1")))
                .andExpect(jsonPath("$[0].dateOfOrder", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$[0].dateOfCarRental", is(LocalDate.now().plusDays(2).toString())))
                .andExpect(jsonPath("$[0].dateOfReturnCar", is(LocalDate.now().plusDays(5).toString())))
                .andExpect(jsonPath("$[0].statusOrder", is(false)))
                .andExpect(jsonPath("$[0].prize", is(400.0)))
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[0].carId", is(1)))
                .andExpect(jsonPath("$[0].equipments", is("cb-radio")));
    }

    @Test
    public void shouldGetOrder() throws Exception {
        //Given
        OrderDto orderDto = new OrderDto(
                1L, "order/number/1", LocalDate.now(),
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(5),
                false, 400.0, 1L, 1L, "cb-radio"
        );

        Order order = new Order(
                1L,
                "order/number/1",
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(5),
                false,
                400.0,
                "cb-radio",
                new User(),
                new Car()
        );

        Long orderId = 1L;
        when(orderService.getOrder(orderId)).thenReturn(Optional.ofNullable(order));
        when(orderMapper.mapToOrderDto(order)).thenReturn(orderDto);

        //When & Then
        mockMvc.perform(get("/v1/orders/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.orderNumber", is("order/number/1")))
                .andExpect(jsonPath("$.dateOfOrder", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.dateOfCarRental", is(LocalDate.now().plusDays(2).toString())))
                .andExpect(jsonPath("$.dateOfReturnCar", is(LocalDate.now().plusDays(5).toString())))
                .andExpect(jsonPath("$.statusOrder", is(false)))
                .andExpect(jsonPath("$.prize", is(400.0)))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.carId", is(1)))
                .andExpect(jsonPath("$.equipments", is("cb-radio")));
    }

    @Test
    public void shouldCreateOrder() throws Exception {
        //Given
        CreateOrderDto createOrderDto = new CreateOrderDto(
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(6),
                1L,
                1L,
                "Cb-radio"
        );

        OrderDto orderDto = new OrderDto(
                1L, "order/number/1", LocalDate.now(),
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(6),
                false, 400.0, 1L, 1L, "cb-radio"
        );

        when(orderMapper.mapToOrderDto(orderService.saveOrder(orderMapper.mapToOrder(createOrderDto)))).thenReturn(orderDto);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(createOrderDto);

        //When & Then
        mockMvc.perform(post("/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.orderNumber", is("order/number/1")))
                .andExpect(jsonPath("$.dateOfOrder", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.dateOfCarRental", is(LocalDate.now().plusDays(2).toString())))
                .andExpect(jsonPath("$.dateOfReturnCar", is(LocalDate.now().plusDays(6).toString())))
                .andExpect(jsonPath("$.statusOrder", is(false)))
                .andExpect(jsonPath("$.prize", is(400.0)))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.carId", is(1)))
                .andExpect(jsonPath("$.equipments", is("cb-radio")));
    }

    @Test
    public void shouldDeleteOrder() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(delete("/v1/orders/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateStatusOrder() throws Exception {
        //Given
        Long orderID = 1L;
        boolean status = true;
        OrderDto orderDto = new OrderDto(
                1L, "order/number/1", LocalDate.now(),
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(6),
                true, 400.0, 1L, 1L, "cb-radio"
        );

        Order order = new Order(
                1L,
                "order/number/1",
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(6),
                false,
                400.0,
                "cb-radio",
                new User(),
                new Car()
        );

        Order order1 = new Order(
                1L,
                "order/number/1",
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(6),
                true,
                400.0,
                "cb-radio",
                new User(),
                new Car()
        );
        when(orderService.getOrder(orderID)).thenReturn(Optional.ofNullable(order));
        when(orderService.saveUpdateStatusOrder(order)).thenReturn(order1);
        when(orderMapper.mapToOrderDto(order1)).thenReturn(orderDto);

        //When & then
        mockMvc.perform(put("/v1/orders/1/status/true")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldActivateOrder() throws Exception {
        //Given
        Long orderID = 1L;
        doNothing().when(orderService).activatingOrder(orderID);

        //Given & Then
        mockMvc.perform(put("/v1/orders/activation/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());

        Mockito.verify(orderService,times(1)).activatingOrder(orderID);
    }
}