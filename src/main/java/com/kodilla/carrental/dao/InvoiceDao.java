package com.kodilla.carrental.dao;

import com.kodilla.carrental.domain.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDao extends CrudRepository<Invoice, Long> {

    @Override
    List<Invoice> findAll();
}
