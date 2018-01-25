package com.gorankadir.se.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gorankadir.se.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	public Customer findByEmail(String email);
	public Customer findByUsername(String username);
}
