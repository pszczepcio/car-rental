package com.kodilla.carrental.service;

import com.kodilla.carrental.dao.InvoiceDao;
import com.kodilla.carrental.dao.OrderDao;
import com.kodilla.carrental.dao.UserDao;
import com.kodilla.carrental.domain.Invoice;
import com.kodilla.carrental.domain.Order;
import com.kodilla.carrental.domain.User;
import com.kodilla.carrental.exception.OrderNotFoundException;
import com.kodilla.carrental.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    public Invoice saveInvoice (final Invoice invoice) throws UserNotFoundException, OrderNotFoundException {
        Invoice newInvoice = invoiceDao.save(invoice);
        String orderNumber = newInvoice.getOrder().getOrderNumber();
        newInvoice.setInvoiceNumber(changeOrderToInvoice(orderNumber));
        addInvoiceToUserList(newInvoice);
        changeOrderStatus(invoice.getOrder().getId());
        return invoiceDao.save(newInvoice);
    }

    public List<Invoice> getInvoiceList() {
        return invoiceDao.findAll();
    }

    public Optional<Invoice> getInvoice (Long id) {
        return invoiceDao.findById(id);
    }

    public void deleteInvoice (Long id) {
        invoiceDao.deleteById(id);
    }

    private String changeOrderToInvoice (String orderNumber) {
        return orderNumber.replace("Order", "Invoice");
    }

    private void changeOrderStatus (final Long orderId) throws OrderNotFoundException {
        Order order = orderDao.findById(orderId).orElseThrow(OrderNotFoundException::new);
        order.setStatusOrder(true);
        orderDao.save(order);
    }

    private void addInvoiceToUserList (final Invoice invoice) throws UserNotFoundException {
        User user = userDao.findById(invoice.getUser().getId()).orElseThrow(UserNotFoundException::new);
        user.getInvoiceList().add(invoice);
        userDao.save(user);
    }
}
