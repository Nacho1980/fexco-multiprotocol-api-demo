package com.demo.multiprotocol.server.api.http;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.demo.multiprotocol.server.model.Car;
import com.demo.multiprotocol.server.repository.CarRepository;
import com.demo.multiprotocol.server.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration tests for the HTTP controller
 * 
 * @author Ignacio Santos
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class CarControllerTestsIT {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private CarService carService;

	@Autowired
	private CarRepository carRepository;

	private List<Car> dbCars = new ArrayList<>();

	@BeforeAll
	void initDatabase() {
		Car rolls = carService
				.createCar(new Car("Rolls Royce", "Phantom", "White", 2019, 210000, "Madrid - Las Rozas"));
		Car porsche = carService
				.createCar(new Car("Porsche", "Panamera", "Black", 2020, 120000, "Sevilla - Carretera amarilla"));
		Car lambo = carService
				.createCar(new Car("Lamborghini", "Aventador", "Yellow", 2020, 340000, "Barcelona - Diagonal"));
		dbCars.add(rolls);
		dbCars.add(porsche);
		dbCars.add(lambo);

	}

	@AfterAll
	void dropDatabase() {
		carRepository.deleteAll();
	}

	@DisplayName("Test get all cars")
	@Test
	@Order(1)
	public void testGetAllCars() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/v1/cars").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
	}

	@DisplayName("Test get car by id")
	@Test
	@Order(2)
	public void testGetCarById() throws Exception {
		long carId = dbCars.get(2).getId();
		mvc.perform(MockMvcRequestBuilders.get("/api/v1/cars/{id}", carId).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(content()
						.json("{\"price\":340000,\"brand\":\"Lamborghini\",\"model\":\"Aventador\",\"year\":2020}"));
	}

	@DisplayName("Test create car")
	@Test
	@Order(3)
	public void testCreateCar() throws Exception {
		// Given
		Car ferrari = new Car("Ferrari", "812 Superfast", "Red", 2019, 290000, "Madrid - Las Rozas");

		// When - Then
		mvc.perform(MockMvcRequestBuilders.post("/api/v1/new").content(asJsonString(ferrari))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Ferrari"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.model").value("812 Superfast"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.color").value("Red"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(290000))
				.andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2019))
				.andExpect(MockMvcResultMatchers.jsonPath("$.location").value("Madrid - Las Rozas"));
	}

	@DisplayName("Test update car")
	@Test
	@Order(4)
	public void testUpdateCar() throws Exception {
		// Given
		Car bugatti = new Car("Bugatti", "Chiron", "Grey", 2019, 1490000, "Madrid - Las Rozas");

		// When
		long carId = dbCars.get(0).getId();
		// Then
		mvc.perform(MockMvcRequestBuilders.put("/api/v1/cars/{id}", carId).content(asJsonString(bugatti))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Bugatti"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Chiron"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(1490000))
				.andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2019))
				.andExpect(MockMvcResultMatchers.jsonPath("$.location").value("Madrid - Las Rozas"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.color").value("Grey"));
	}

	@DisplayName("Test delete car")
	@Test
	@Order(5)
	public void testDeleteCar() throws Exception {
		// Given
		long carId = dbCars.get(0).getId();
		// When
		mvc.perform(MockMvcRequestBuilders.delete("/api/v1/cars/{id}", carId)).andExpect(status().isAccepted());
		// Then
		mvc.perform(MockMvcRequestBuilders.get("/api/v1/cars/{id}", carId).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	public static String asJsonString(final Object obj) {
		try {
			String json = new ObjectMapper().writeValueAsString(obj);
			return json;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
