package com.globant.mentorship.itsl.customer_service.infrastucture.repository;

import com.globant.mentorship.itsl.customer_service.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
