package com.demo.multiprotocol.server.api.http;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.demo.multiprotocol.server.model.Car;
import com.demo.multiprotocol.server.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration tests for the HTTP controller using mocks
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

	@MockBean
	private CarService carService;

	private List<Car> allCars = new ArrayList<>();

	@BeforeAll
	void initMocks() {
		Car rolls = new Car("Rolls Royce", "Phantom", "White", 210000, "Madrid - Las Rozas");
		Car porsche = new Car("Porsche", "Panamera", "Black", 120000, "Sevilla - Carretera amarilla");
		Car lambo = new Car("Lamborghini", "Aventador SVJ", "Yellow", 340000, "Barcelona - Diagonal");
		allCars.add(rolls);
		allCars.add(porsche);
		allCars.add(lambo);
	}

	@DisplayName("Test get all cars")

	@Test
	@Order(1)
	public void testGetAllCars() {
		try {
			Mockito.when(carService.getAllCars()).thenReturn(allCars);
			mvc.perform(MockMvcRequestBuilders.get("/api/v1/cars").accept(MediaType.APPLICATION_JSON)).andDo(print())
					.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
		} catch (Exception e) {
			fail("Test to get all cars failed", e);
		}
	}

	@DisplayName("Test get car by id")

	@Test
	@Order(2)
	public void testGetCarById() {
		try {
			Mockito.when(carService.getCarById(2l)).thenReturn(allCars.get(2));
			mvc.perform(MockMvcRequestBuilders.get("/api/v1/cars/{id}", 2l).accept(MediaType.APPLICATION_JSON))
					.andDo(print()).andExpect(status().isOk())
					.andExpect(
							content().json("{\"price\":340000,\"brand\":\"Lamborghini\",\"model\":\"Aventador SVJ\"}"))
					.andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Lamborghini"));
		} catch (Exception e) {
			fail("Test get car by id failed", e);
		}
	}

	@DisplayName("Test create car")

	@Test
	@Order(3)
	public void testCreateCar() {
		// Given-When
		Car ferrari = new Car("Ferrari", "812 Superfast", "Red", 290000, "Madrid - Las Rozas");
		Mockito.when(carService.createCar(any(Car.class))).thenReturn(ferrari);

		// Then
		try {
			mvc.perform(MockMvcRequestBuilders.post("/api/v1/new").content(asJsonString(ferrari))
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().json("{\"price\":290000,\"brand\":\"Ferrari\",\"model\":\"812 Superfast\"}"))
					.andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Ferrari"))
					.andExpect(MockMvcResultMatchers.jsonPath("$.model").value("812 Superfast"))
					.andExpect(MockMvcResultMatchers.jsonPath("$.color").value("Red"))
					.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(290000))
					.andExpect(MockMvcResultMatchers.jsonPath("$.location").value("Madrid - Las Rozas"));
		} catch (Exception e) {
			fail("Test to create a car failed", e);
		}
	}

	@DisplayName("Test update car")
	@Test
	@Order(4)
	public void testUpdateCar() throws Exception {
		// Given When
		Car bugatti = new Car("Bugatti", "Chiron", "Grey", 1490000, "Madrid - Las Rozas");
		Mockito.when(carService.updateCar(any(Long.class), any(Car.class))).thenReturn(bugatti);

		// Then
		try {
			mvc.perform(MockMvcRequestBuilders.put("/api/v1/cars/{id}", 1l).content(asJsonString(bugatti))
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
					.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Bugatti"))
					.andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Chiron"))
					.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(1490000))
					.andExpect(MockMvcResultMatchers.jsonPath("$.location").value("Madrid - Las Rozas"))
					.andExpect(MockMvcResultMatchers.jsonPath("$.color").value("Grey"));
		} catch (Exception e) {
			fail("Test to update a car failed", e);
		}
	}

	@DisplayName("Test delete car")

	@Test
	@Order(5)
	public void testDeleteCar() {
		try {
			mvc.perform(MockMvcRequestBuilders.delete("/api/v1/cars/{id}", 1l)).andExpect(status().isAccepted());
		} catch (Exception e) {
			fail("Test to delete a car failed", e);
		}
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
