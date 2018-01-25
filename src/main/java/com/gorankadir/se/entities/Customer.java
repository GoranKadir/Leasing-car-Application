package com.gorankadir.se.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;
import com.gorankadir.se.controller.CustomerController;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long customerid;

	@Column(name = "password")
	private String password;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "name")
	private String username;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE)
	@JsonView(CustomerController.class)
	private List<Car> cars;


	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "customerid", referencedColumnName = "customerid"), inverseJoinColumns = @JoinColumn(name = "roleid", referencedColumnName = "roleid"))
	private List<Roles> role;

	public List<Car> addCars(Car car) {
		car.setCustomer(this);
		cars.add(car);
		return cars;
	}

	public Customer() {
	}

	public Customer(String password, String email, String username) {
		this.password = password;
		this.email = email;
		this.username = username;
	}

	public List<Roles> getRole() {
		return role;
	}

	public void setRole(List<Roles> role) {
		this.role = role;
	}

	public long getCustomerid() {
		return customerid;
	}

	public void setCustomerId(long customerid) {
		this.customerid = customerid;
	}

	public boolean checkEmail(String email) {
		if (email.equals(email)) {
			return true;
		} else {
			return false;
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
