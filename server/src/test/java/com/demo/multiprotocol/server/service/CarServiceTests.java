package com.demo.multiprotocol.server.service;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.demo.multiprotocol.server.model.Car;
import com.demo.multiprotocol.server.repository.CarRepository;

/**
 * Unit test for the service
 * 
 * @author Ignacio Santos
 *
 */
@SpringBootTest
public class CarServiceTests {
	@Autowired
	private CarRepository carRepo;
	@Autowired
	private CarService carService;

	@BeforeEach
	void setUp() {
		carRepo.save(new Car("Rolls Royce", "Phantom", "White", 210000, "Madrid - Finest cars Las Rozas"));
		carRepo.save(new Car("Porsche", "Panamera", "Black", 120000, "Sevilla - Deluxe motor Palmera"));
		carRepo.save(new Car("Lamborghini", "Aventador", "Yellow", 340000, "Barcelona - VIP cars Diagonal"));
	}

	@AfterEach
	void tearDown() {
		carRepo.deleteAll();
	}

	@Test
	@DisplayName("Test get all cars")
	@Order(1)
	public void testGetAllCars() {
		// When
		List<Car> allCars = carService.getAllCars();
		// Then
		Assert.isTrue(allCars.size() == 3, "Incorrect number of cars retrieved");
	}

	@Test
	@DisplayName("Test get car by id")
	@Order(2)
	public void testGetCarById() {
		try {
			// Given
			Long idRolls = carService.getAllCars().get(0).getId();
			// When
			Car rollsRoyce = carService.getCarById(idRolls);
			// Then
			Assert.isTrue(rollsRoyce.getBrand().equals("Rolls Royce"), "Incorrect brand");
			Assert.isTrue(rollsRoyce.getModel().equals("Phantom"), "Incorrect model");
		} catch (Exception e) {
			fail("Get car should not have thrown any exception");
		}
	}

	@Test
	@DisplayName("Test create car")
	@Order(3)
	public void testCreateCar() {
		// Given
		Car ferrari = new Car("Ferrari", "812 Superfast", "Red", 290000, "Madrid - Las Rozas");
		// When
		Car ferrariStored = carService.createCar(ferrari);
		// Then
		Assert.isTrue(ferrariStored.getId() > 0, "Car not saved");
	}

	@Test
	@DisplayName("Test update car")
	@Order(4)
	public void testUpdateCar() {
		// Given
		Car ferrari = new Car("Ferrari", "812 Superfast", "Yellow", 290000, "Madrid - Las Rozas");
		try {
			// When
			Car updatedCar = carService.updateCar(3l, ferrari);
			// Then
			Assert.isTrue(updatedCar.getColor().equals("Yellow"), "Car not updated");
		} catch (Exception e) {
			fail("Update car should not have thrown any exception");
		}
	}

	@Test
	@DisplayName("Test delete car")
	@Order(5)
	public void testDeleteCar() {
		try {
			// Given
			Long idLambo = carService.getAllCars().get(2).getId();
			// When
			carService.deleteCar(idLambo); // Delete the Lamborghini
			List<Car> allCars = carService.getAllCars();
			// Then
			Assert.isTrue(allCars.size() == 2, "Car not deleted");
			Assert.isTrue(allCars.stream().filter(car -> car.getBrand().equals("Lamborghini")).count() == 0,
					"Car not deleted");
		} catch (Exception e) {
			fail("Delete car should not have thrown any exception");
		}
	}
}
