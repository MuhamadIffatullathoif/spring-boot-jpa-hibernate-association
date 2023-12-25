package com.iffat.springboot.association.repositories;

import com.iffat.springboot.association.entities.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
