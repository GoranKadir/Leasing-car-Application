package com.gorankadir.se.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.gorankadir.se.entities.Customer;
import com.gorankadir.se.repository.CustomerRepository;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;
	
	
	
	@PostMapping
	public ResponseEntity<Void> saveCustomer(@RequestBody Customer customer){
		customerRepository.save(customer);
		HttpHeaders header = new HttpHeaders();
		return new ResponseEntity<Void>(header, HttpStatus.CREATED);
	}
	
	
	@GetMapping
	@JsonView(CustomerController.class)
	public ResponseEntity<List<Customer>>getAllCustomers(){
		List<Customer> customerList = customerRepository.findAll();
		if(customerList.size() == 0){
			return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Customer>>(customerList, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	@JsonView(CustomerController.class)
	public ResponseEntity<Customer> getCustomerById(@PathVariable ("id") long id){
		Customer customer = customerRepository.findOne(id);
		if(customer == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(customer);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Void> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer){
		customer.setCustomerId(id);
		customerRepository.saveAndFlush(customer);
		HttpHeaders header = new HttpHeaders();
		return new ResponseEntity<Void>(header, HttpStatus.OK);	
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable ("id") long id){
		customerRepository.delete(id);
		return ResponseEntity.ok().build();
	}
	
	
}
