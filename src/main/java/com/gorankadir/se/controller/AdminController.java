package com.gorankadir.se.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.gorankadir.se.entities.Car;
import com.gorankadir.se.entities.Customer;
import com.gorankadir.se.repository.CarRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	CarRepository carRepository;

	@RequestMapping("/showusers")
	public String getCustomers(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Customer[]> customers = restTemplate.getForEntity("http://localhost:8080/api/customers",
				Customer[].class);
		model.addAttribute("customers", customers.getBody());
		return "showallusers";
	}
	
	@RequestMapping("/showcars")
    public String showCarTable(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Car[]> cars = restTemplate.getForEntity("http://localhost:8080/api/cars", Car[].class);
        model.addAttribute("cars", cars.getBody());
        model.addAttribute("newcars", new Car());
        return "showcars";
    }
	
	@RequestMapping(path="/showcars", method=RequestMethod.POST)
	public String postCars(@ModelAttribute Car newcars, Model model){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForEntity("http://localhost:8080/api/cars", newcars, Object.class);
		return showCarTable(model);
	}
	
	@DeleteMapping("/deletecar/{id}")
	public ResponseEntity<Void> deleteCar(@PathVariable(value = "id") long carId){
		HttpHeaders header = new HttpHeaders();
		Car car = carRepository.findOne(carId);
			carRepository.delete(car);
		return new ResponseEntity<Void>(header, HttpStatus.CREATED);
	}
	
//	@PutMapping("/updatecar/{id}")
//	public ResponseEntity<Void> updateCar(@PathVariable("id") long id, @RequestBody Car car) {
//		car.setCarid(id);
//		carRepository.saveAndFlush(car);
//		HttpHeaders header = new HttpHeaders();
//		return new ResponseEntity<Void>(header, HttpStatus.OK);
//	}
	
	@RequestMapping("/updatecar/{id}")
	public String editCar(@PathVariable ("id") long id, @ModelAttribute Car car, Model model) {
		car.setCarid(id);
		model.addAttribute("cars", carRepository.findOne(id));
		car = carRepository.saveAndFlush(car);
		return "updatecars";
	}
	
	
		
	
	
	
	
	
	
	
	
	
}
