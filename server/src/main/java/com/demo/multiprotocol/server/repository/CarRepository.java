package com.demo.multiprotocol.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.multiprotocol.server.model.Car;

/**
 * The JPA interface for the car repository. The database can be changed in the
 * application.properties
 *
 * @author Ignacio Santos
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}