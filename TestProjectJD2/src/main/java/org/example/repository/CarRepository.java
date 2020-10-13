package org.example.repository;

import org.example.domain.Car;

import java.util.List;

public interface CarRepository extends CrudRepository<Long, Car> {

    List<Car> search(String query);
}
