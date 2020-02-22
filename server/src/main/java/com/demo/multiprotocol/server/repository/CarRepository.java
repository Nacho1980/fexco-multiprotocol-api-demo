package com.demo.multiprotocol.server.repository;

import com.demo.multiprotocol.server.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Car repository.
 *
 * @author Ignacio Santos
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {}