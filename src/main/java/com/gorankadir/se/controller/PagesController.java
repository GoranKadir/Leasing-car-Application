package com.gorankadir.se.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.gorankadir.se.entities.Car;
import com.gorankadir.se.entities.Customer;
import com.gorankadir.se.repository.CarRepository;
import com.gorankadir.se.repository.CustomerRepository;

@Controller
public class PagesController {
	
	@Autowired
	CarRepository carRepository;

	@Autowired
	CustomerRepository customerRepository;

	@RequestMapping(value = "/cars")
    public String showCars(HttpServletRequest req, Model model) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Car[]> cars = restTemplate.getForEntity("http://localhost:8080/api/cars", Car[].class);
        model.addAttribute("cars", cars.getBody());
        return "cars";
    }
	
	@RequestMapping("/profile")
	public String profile(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Customer customer = customerRepository.findByUsername(username);
		model.addAttribute("info", customer);
		return "profile";
	}
			
}
