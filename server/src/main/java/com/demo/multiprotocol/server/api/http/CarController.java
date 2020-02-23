package com.demo.multiprotocol.server.api.http;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.multiprotocol.server.api.amqp.Publisher;
import com.demo.multiprotocol.server.exception.ResourceNotFoundException;
import com.demo.multiprotocol.server.model.Car;
import com.demo.multiprotocol.server.service.CarService;

@RestController
@RequestMapping("/api/v1")
public class CarController {
	@Autowired
	private Publisher publisher;
	@Autowired
	private CarService carService;

	/**
	 * Get all cars list.
	 *
	 * @return the list
	 */
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/cars")
	public List<Car> getAllCars() {
		return carService.getAllCars();
	}

	/**
	 * Gets cars by id.
	 *
	 * @param carId the car id
	 * @return the cars by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/cars/{id}")
	public ResponseEntity<Car> getCarById(@PathVariable(value = "id") Long carId) throws ResourceNotFoundException {
		Car car = carService.getCarById(carId);
		return ResponseEntity.ok().body(car);
	}

	/**
	 * Create car.
	 *
	 * @param car the car
	 * @return the car
	 */
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/new")
	public ResponseEntity<Car> createCar(@RequestBody Car car) {
		Car newCar = carService.createCar(car);
		publisher.sendMessage("New supercar in stock! Our " + car.getLocation() + " dealer has received a new "
				+ car.getBrand() + " " + car.getModel() + " in color " + car.getColor() + " with a price of "
				+ car.getPrice() + " euros");
		return ResponseEntity.ok(newCar);
	}

	/**
	 * Update car.
	 *
	 * @param carId      the car id
	 * @param carDetails the car details
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@CrossOrigin(origins = "http://localhost:3000")
	@PutMapping("/cars/{id}")
	public ResponseEntity<Car> updateCar(@PathVariable(value = "id") Long carId, @Valid @RequestBody Car carDetails)
			throws ResourceNotFoundException {
		Car updatedCar = carService.updateCar(carId, carDetails);
		return ResponseEntity.ok(updatedCar);
	}

	/**
	 * Delete car.
	 *
	 * @param carId the car id
	 * @return the map
	 * @throws Exception the exception
	 */
	@CrossOrigin(origins = "http://localhost:3000")
	@DeleteMapping("/cars/{id}")
	public ResponseEntity<HttpStatus> deleteCar(@PathVariable(value = "id") Long carId) throws Exception {
		carService.deleteCar(carId);
		return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}

}
