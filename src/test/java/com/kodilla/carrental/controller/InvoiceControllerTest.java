package com.kodilla.carrental.controller;

import com.google.gson.*;
import com.kodilla.carrental.domain.Invoice;
import com.kodilla.carrental.domain.Order;
import com.kodilla.carrental.domain.User;
import com.kodilla.carrental.dto.*;
import com.kodilla.carrental.exception.*;
import com.kodilla.carrental.localdateadapter.LocalDateAdapter;
import com.kodilla.carrental.dao.CarDao;
import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.mapper.CarMapper;
import com.kodilla.carrental.mapper.InvoiceMapper;
import com.kodilla.carrental.mapper.OrderMapper;
import com.kodilla.carrental.service.CarService;
import com.kodilla.carrental.service.InvoiceService;
import com.kodilla.carrental.service.OrderService;
import javafx.beans.binding.When;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @MockBean
    private InvoiceMapper invoiceMapper;

    @Test
    public void shouldGetInvoiceDto() throws Exception {
        //Given
        InvoiceDto invoiceDto = new InvoiceDto(
                1L,
                "invoice/number/1",
                1L,
                1L
        );


        Invoice invoice = new Invoice(
                1L, "invoice/number/1", new User() , new Order());
        Long invoiceId = 1L;

        when(invoiceService.getInvoice(invoiceId)).thenReturn(Optional.ofNullable(invoice));
        when(invoiceMapper.mapToInvoiceDto(invoice)).thenReturn(invoiceDto);

        //When & Then
        mockMvc.perform(get("/v1/invoices/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.invoiceNumber", is("invoice/number/1")))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.orderId", is(1)));

    }

    @Test
    public void shouldGetInvoiceDtoList() throws Exception {
        //Given
        List<GetInvoiceDto> getInvoiceDtoList = new ArrayList<>();
        getInvoiceDtoList.add(new GetInvoiceDto(1L , "invoice/number/1"));

        when(invoiceMapper.getInvoiceDtoList(invoiceService.getInvoiceList())).thenReturn(getInvoiceDtoList);

        //When & then
        mockMvc.perform(get("/v1/invoices")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].invoiceNumber", is("invoice/number/1")));
    }

    @Test
    public void shouldCreateInvoice() throws Exception {
        //Given
        CreateInvoiceDto createInvoiceDto = new CreateInvoiceDto(1L , 1L);
        InvoiceDto invoiceDto = new InvoiceDto(1L,"invoice/number/1",
                                                1L, 1L);

        when(invoiceMapper.mapToInvoiceDto(invoiceService.saveInvoice(invoiceMapper.mapToInvoice(createInvoiceDto))))
                .thenReturn(invoiceDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(createInvoiceDto);

        //When & Then
        mockMvc.perform(post("/v1/invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.invoiceNumber", is("invoice/number/1")))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.orderId", is(1)));
    }

    @Test
    public void shouldDeleteInvoice() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(delete("/v1/invoices/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}