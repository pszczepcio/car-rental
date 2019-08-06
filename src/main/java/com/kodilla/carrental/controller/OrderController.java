package com.kodilla.carrental.controller;

import com.kodilla.carrental.domain.Order;
import com.kodilla.carrental.dto.CreateOrderDto;
import com.kodilla.carrental.dto.OrderDto;
import com.kodilla.carrental.dto.UpdateOrderStatus;
import com.kodilla.carrental.exception.AdditionalEquipmentNotFoundException;
import com.kodilla.carrental.exception.CarNotFoundException;
import com.kodilla.carrental.exception.OrderNotFoundException;
import com.kodilla.carrental.exception.UserNotFoundException;
import com.kodilla.carrental.mapper.OrderMapper;
import com.kodilla.carrental.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
@Transactional
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET, value = "/orders")
    public List<OrderDto> getOrders(){
        return orderMapper.mapToOrderDtoList(orderService.getOrders());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orders/{orderId}")
    public OrderDto getOrder(@PathVariable Long orderId) throws OrderNotFoundException {
        return orderMapper.mapToOrderDto(orderService.getOrder(orderId).orElseThrow(OrderNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/orders")
    public OrderDto createOrder (@RequestBody CreateOrderDto createOrderDto) throws CarNotFoundException, UserNotFoundException, AdditionalEquipmentNotFoundException {
        return orderMapper.mapToOrderDto(orderService.saveOrder(orderMapper.mapToOrder(createOrderDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/orders/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/orders/{orderID}/status/{status}")
    public OrderDto updateStatusOrder (@PathVariable Long orderID, @PathVariable boolean status) throws OrderNotFoundException {
        Optional<Order> order = orderService.getOrder(orderID);
        order.get().setStatusOrder(status);
        return orderMapper.mapToOrderDto(orderService.saveUpdateStatusOrder(order.orElseThrow(OrderNotFoundException::new)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/orders/activation/{orderID}")
    public void activateOrder (@PathVariable Long orderID) throws OrderNotFoundException, CarNotFoundException, AdditionalEquipmentNotFoundException {
         orderService.activatingOrder(orderID);
    }
}
