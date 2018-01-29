package com.gorankadir.se.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonView;
import com.gorankadir.se.entities.Car;
import com.gorankadir.se.entities.Customer;
import com.gorankadir.se.repository.CarRepository;
import com.gorankadir.se.repository.CustomerRepository;

@Controller
@RequestMapping("/bookcar")
public class LeasingController {
	
	@Autowired
	CarRepository carRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	
	@PostMapping("{id}")
	public ResponseEntity<Void> createBooking(@PathVariable(value = "id") long carId){
		HttpHeaders header = new HttpHeaders();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Customer customer = customerRepository.findByUsername(username);
		Car car = carRepository.findOne(carId);
			customer.addCars(car);
			customerRepository.save(customer);
		return new ResponseEntity<Void>(header, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/cars")
    public String messages(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Car[]> cars = restTemplate.getForEntity("http://localhost:8080/api/cars", Car[].class);
        model.addAttribute("cars", cars.getBody());
        return "cars";
    }
	


}
