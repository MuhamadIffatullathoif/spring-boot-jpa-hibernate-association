package com.iffat.springboot.association.repositories;

import com.iffat.springboot.association.entities.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceInterface extends CrudRepository<Invoice, Long> {
}
