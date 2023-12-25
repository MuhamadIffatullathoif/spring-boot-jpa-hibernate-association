package com.iffat.springboot.association.repositories;

import com.iffat.springboot.association.entities.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query("SELECT c FROM Client c JOIN FETCH c.addresses WHERE c.id =?1")
    Optional<Client> findOne(Long id);
}
