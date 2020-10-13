package org.example.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.example.domain.Car;
import org.example.repository.CarRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarRepositoryImpl implements CarRepository {

    public static final String POSTGRES_DRIVER_NAME = "org.postgresql.Driver";
    public static final String DATABASE_URL = "jdbc:postgresql://localhost:";
    public static final int DATABASE_PORT = 5432;
    public static final String DATABASE_NAME = "/webinar_database";
    public static final String DATABASE_LOGIN = "postgres";
    public static final String DATABASE_PASSWORD = "root";

    private final static String ID = "id";
    private final static String MODEL = "model";
    private final static String CREATION_YEAR = "creation_year";
    private final static String USER_ID = "user_id";
    private final static String PRICE = "price";
    private final static String COLOR = "color";

    @Override
    public List<Car> search(String query) {
        return null;
    }

    @Override
    public Car save(Car object) {
        return null;
    }

    @Override
    public List<Car> findAll() {

        final String findAllQuery = "select * from m_cars order by id";

        List<Car> result = new ArrayList<>();

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            Class.forName(POSTGRES_DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC driver cannot be loaded!!!");
            throw new RuntimeException("JDBC driver cannot be loaded!");
        }

        String jdbcURL = StringUtils.join(DATABASE_URL, DATABASE_PORT, DATABASE_NAME);

        try {
            connection = DriverManager.getConnection(jdbcURL, DATABASE_LOGIN, DATABASE_PASSWORD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(findAllQuery);

            while (resultSet.next()) {
                Car car = new Car();
                car.setId(resultSet.getLong(ID));
                car.setModel(resultSet.getString(MODEL));
                car.setCreation_year(resultSet.getInt(CREATION_YEAR));
                car.setUser_iq(resultSet.getLong(USER_ID));
                car.setPrice(resultSet.getDouble(PRICE));
                car.setColor(resultSet.getString(COLOR));

                result.add(car);

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }

        return null;
    }

    @Override
    public Car findById(Long key) {
        return null;
    }

    @Override
    public Optional<Car> findOne(Long key) {
        return Optional.empty();
    }

    @Override
    public Car update(Car object) {
        return null;
    }

    @Override
    public Long delete(Car object) {
        return null;
    }
}
