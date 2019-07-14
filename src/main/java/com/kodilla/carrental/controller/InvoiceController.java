package com.kodilla.carrental.controller;

import com.kodilla.carrental.dto.CreateInvoiceDto;
import com.kodilla.carrental.dto.GetInvoiceDto;
import com.kodilla.carrental.dto.InvoiceDto;
import com.kodilla.carrental.exception.InvoiceNotFoundException;
import com.kodilla.carrental.mapper.InvoiceMapper;
import com.kodilla.carrental.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/v1")
@Transactional
public class InvoiceController {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private InvoiceService invoiceService;

    @RequestMapping(method = RequestMethod.GET, value = "/invoices/{invoiceId}")
    public InvoiceDto getInvoiceDto (@PathVariable Long invoiceId) throws InvoiceNotFoundException {
        return invoiceMapper.mapToInvoiceDto(invoiceService.getInvoice(invoiceId).orElseThrow(InvoiceNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/invoices")
    public List<GetInvoiceDto> getInvoiceDtoList() {
        return invoiceMapper.getInvoiceDtoList(invoiceService.getInvoiceList());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/invoices")
    public InvoiceDto createInvoice (@RequestBody CreateInvoiceDto createInvoiceDto) throws Exception {
        return invoiceMapper.mapToInvoiceDto(invoiceService.saveInvoice(invoiceMapper.mapToInvoice(createInvoiceDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/invoices/{invoiceId}")
    public void deleteInvoice (@PathVariable Long invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
    }
}
