package com.demo.multiprotocol.server.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.multiprotocol.server.exception.ResourceNotFoundException;
import com.demo.multiprotocol.server.model.Car;
import com.demo.multiprotocol.server.repository.CarRepository;

/**
 * The implementation of the service for the car dealer
 * 
 * @author Ignacio Santos
 *
 */
@Service
public class CarServiceImpl implements CarService {
	@Autowired
	private CarRepository carRepository;

	/**
	 * Get all cars list.
	 *
	 * @return the list
	 */
	public List<Car> getAllCars() {
		return carRepository.findAll();
	}

	/**
	 * Gets cars by id.
	 *
	 * @param carId the car id
	 * @return the cars by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	public Car getCarById(Long carId) throws ResourceNotFoundException {
		Car car = carRepository.findById(carId)
				.orElseThrow(() -> new ResourceNotFoundException("Car not found on :: " + carId));
		return car;
	}

	/**
	 * Create car.
	 *
	 * @param car the car
	 * @return the car
	 */
	public Car createCar(Car car) {
		Car newCar = carRepository.save(car);
		return newCar;
	}

	/**
	 * Update car.
	 *
	 * @param carId      the car id
	 * @param carDetails the car details
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	public Car updateCar(Long carId, Car carDetails) throws ResourceNotFoundException {
		Car car = carRepository.findById(carId)
				.orElseThrow(() -> new ResourceNotFoundException("Car not found on :: " + carId));
		car.setBrand(carDetails.getBrand());
		car.setLocation(carDetails.getLocation());
		car.setModel(carDetails.getModel());
		car.setPrice(carDetails.getPrice());
		car.setYear(carDetails.getYear());
		car.setColor(carDetails.getColor());
		car.setUpdatedAt(new Date());
		final Car updatedCar = carRepository.save(car);
		return updatedCar;
	}

	/**
	 * Delete car.
	 *
	 * @param carId the car id
	 * @return the map
	 * @throws Exception the exception
	 */
	public void deleteCar(Long carId) throws Exception {
		Car car = carRepository.findById(carId)
				.orElseThrow(() -> new ResourceNotFoundException("Car not found on :: " + carId));
		carRepository.delete(car);
	}

}
