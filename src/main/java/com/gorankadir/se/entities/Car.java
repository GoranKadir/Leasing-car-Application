package com.gorankadir.se.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.gorankadir.se.controller.CarController;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "car")
public class Car implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long carid;
	
	@Column(name = "name")
	private String carName;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "picurl")
	private String picname;
	
	@ManyToOne
	@JoinColumn(name = "custid", referencedColumnName = "customerid")
	@JsonView(CarController.class)
	private Customer customer;

	
	
	public Car(){}
	

	public Car(long carid, String carName, String color) {
		this.carid = carid;
		this.carName = carName;
		this.color = color;
	}
	
	public long getCarid() {
		return carid;
	}

	public void setCarid(long carid) {
		this.carid = carid;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public String getPicname() {
		return picname;
	}


	public void setPicname(String picname) {
		this.picname = picname;
	}


	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
	
}
