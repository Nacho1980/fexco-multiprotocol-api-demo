package com.demo.multiprotocol.server.api.http;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

/**
 * REST controller that exposes the CRUD endpoints to the client
 * 
 * @author Ignacio Santos
 *
 */
@RestController
@RequestMapping("/api/v1")
public class CarController {

	@Value("${amqp.exchange}")
	private String exchange;
	@Value("${amqp.routing.key}")
	private String routingKey;
	@Autowired
	private Publisher publisher;
	@Autowired
	private CarService carService;

	// This is specified to deal with CORS issues
	// See https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS
	private static final String clientAddress = "http://localhost:3000";

	/**
	 * Get all cars list.
	 *
	 * @return the list
	 */
	@CrossOrigin(origins = clientAddress)
	@GetMapping("/cars")
	public List<Car> getAllCars() {
		List<Car> carList = carService.getAllCars();
		return carList;
	}

	/**
	 * Gets cars by id.
	 *
	 * @param carId the car id
	 * @return the cars by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@CrossOrigin(origins = clientAddress)
	@GetMapping("/cars/{id}")
	public ResponseEntity<Car> getCarById(@PathVariable(value = "id") Long carId) throws ResourceNotFoundException {
		Car car = carService.getCarById(carId);
		return ResponseEntity.ok().body(car);
	}

	/**
	 * Create car and send a AMQP message informing of it
	 *
	 * @param car the car
	 * @return the car
	 */
	@CrossOrigin(origins = clientAddress)
	@PostMapping("/new")
	public ResponseEntity<Car> createCar(@RequestBody Car car) throws Exception {
		Car newCar = carService.createCar(car);
		String newCarMsg = "New supercar in stock! Our " + car.getLocation() + " dealer has received a new "
				+ car.getBrand() + " " + car.getModel() + " in color " + car.getColor() + " with a price of "
				+ car.getPrice() + " euros";
		publisher.sendMessage(newCarMsg);
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
	@CrossOrigin(origins = clientAddress)
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
	@CrossOrigin(origins = clientAddress)
	@DeleteMapping("/cars/{id}")
	public ResponseEntity<HttpStatus> deleteCar(@PathVariable(value = "id") Long carId) throws Exception {
		carService.deleteCar(carId);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

}
