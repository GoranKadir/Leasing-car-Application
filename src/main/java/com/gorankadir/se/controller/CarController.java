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
import com.gorankadir.se.entities.Car;
import com.gorankadir.se.repository.CarRepository;

@RestController
@RequestMapping("/api/cars")
public class CarController {

	@Autowired
	CarRepository carRepository;

	@GetMapping
	@JsonView(CarController.class)
	public ResponseEntity<List<Car>> getCars() {
		List<Car> listcars = carRepository.findAll();
		if (listcars.size() == 0) {
			return new ResponseEntity<List<Car>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Car>>(listcars, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	@JsonView(CarController.class)
	public ResponseEntity<Car> getCarById(@PathVariable(value = "id") long carId) {
		Car car = carRepository.findOne(carId);
		if (car == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(car);
	}

	@PostMapping
	public ResponseEntity<Void> saveCar(@RequestBody Car car) {
		carRepository.save(car);
		HttpHeaders header = new HttpHeaders();
		return new ResponseEntity<Void>(header, HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<Void> updateCar(@PathVariable("id") long id, @RequestBody Car car) {
		car.setCarid(id);
		carRepository.saveAndFlush(car);
		HttpHeaders header = new HttpHeaders();
		return new ResponseEntity<Void>(header, HttpStatus.OK);
	}

	@DeleteMapping("{carid}")
	ResponseEntity<Void> deleteCar(@PathVariable(name = "carid") long id) {
		carRepository.delete(id);
		return ResponseEntity.ok().build();
	}

}
