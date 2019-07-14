package com.kodilla.carrental.mapper;

import com.kodilla.carrental.dao.OrderDao;
import com.kodilla.carrental.dao.UserDao;
import com.kodilla.carrental.domain.Invoice;
import com.kodilla.carrental.dto.CreateInvoiceDto;
import com.kodilla.carrental.dto.GetInvoiceDto;
import com.kodilla.carrental.dto.InvoiceDto;
import com.kodilla.carrental.exception.OrderNotFoundException;
import com.kodilla.carrental.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceMapper {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    public Invoice mapToInvoice (final CreateInvoiceDto createInvoiceDto) throws UserNotFoundException, OrderNotFoundException {
        return new Invoice(
                userDao.findById(createInvoiceDto.getUserId()).orElseThrow(UserNotFoundException::new),
                orderDao.findById(createInvoiceDto.getOrderId()).orElseThrow(OrderNotFoundException::new)
        );
    }

    public List<GetInvoiceDto> getInvoiceDtoList (final List<Invoice> invoiceList) {
        return invoiceList.stream()
                .map(i -> new GetInvoiceDto(
                        i.getId(),
                        i.getInvoiceNumber()))
                .collect(Collectors.toList());
    }

    public InvoiceDto mapToInvoiceDto (final Invoice invoice) {
        return new InvoiceDto(
                invoice.getId(),
                invoice.getInvoiceNumber(),
                invoice.getUser().getId(),
                invoice.getOrder().getId());
    }
}
