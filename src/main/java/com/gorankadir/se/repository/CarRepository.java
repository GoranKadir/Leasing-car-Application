package com.gorankadir.se.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gorankadir.se.entities.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

}
