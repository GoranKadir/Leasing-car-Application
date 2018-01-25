package com.gorankadir.se.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.client.RestTemplate;

import com.gorankadir.se.entities.Customer;
import com.gorankadir.se.entities.Roles;
import com.gorankadir.se.repository.CustomerRepository;

@Controller
public class RegisterController {

	@Autowired
	CustomerRepository customerRepository;
	
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public RegisterController(CustomerRepository customerRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
    	this.customerRepository = customerRepository;
    	this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

	@GetMapping("/membership")
	public String getCustomerList(Model model) {
		model.addAttribute("greeting", new Customer());
		return "registerformula";
	}

	@RequestMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@PostMapping("/membership")
	public String registerForm(@ModelAttribute(name = "greeting")Customer customer) {
		RestTemplate restTemplate = new RestTemplate();
		List<Roles> roles = new ArrayList<>();
		roles.add(new Roles("ROLE_USER"));
		customer.setRole(roles);
		customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
		restTemplate.postForEntity("http://localhost:8080/api/customers", customer, Object.class);
		return "result";
	}

}
