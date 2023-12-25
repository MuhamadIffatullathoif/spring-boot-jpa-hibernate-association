package com.iffat.springboot.association.repositories;

import com.iffat.springboot.association.entities.ClientDetails;
import org.springframework.data.repository.CrudRepository;

public interface ClientDetailsRepository extends CrudRepository<ClientDetails, Long> {
}
