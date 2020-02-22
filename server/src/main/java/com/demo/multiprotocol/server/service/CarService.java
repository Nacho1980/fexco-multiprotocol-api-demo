package com.demo.multiprotocol.server.service;

import java.util.List;

import com.demo.multiprotocol.server.exception.ResourceNotFoundException;
import com.demo.multiprotocol.server.model.Car;

/**
 * Interface for the car service
 * 
 * @author Ignacio Santos
 *
 */
public interface CarService {
	public List<Car> getAllCars();

	public Car getCarById(Long id) throws ResourceNotFoundException;

	public Car createCar(Car car);

	public Car updateCar(Long carId, Car carDetails) throws ResourceNotFoundException;

	public void deleteCar(Long carId) throws Exception;

}
