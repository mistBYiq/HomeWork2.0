package org.example.repository.impl;

import org.example.domain.Car;
import org.example.exception.EntityNotFoundException;
import org.example.repository.CarRepository;
import org.example.util.DatabasePropertiesReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.example.util.DatabasePropertiesReader.DATABASE_DRIVER_NAME;
import static org.example.util.DatabasePropertiesReader.DATABASE_LOGIN;
import static org.example.util.DatabasePropertiesReader.DATABASE_PASSWORD;
import static org.example.util.DatabasePropertiesReader.DATABASE_URL;

public class CarRepositoryImpl implements CarRepository {

    private static final String ID = "id";
    private static final String MODEL = "model";
    private static final String GUARANTEE = "guarantee_expiration_date";
    private static final String PRICE = "price";
    private static final String DEALER_ID = "dealer_id";
    private static final String USER_ID = "user_id";
    private static final String COUNTRY = "country";

    private static final String SEARCH_SQL = "select * from m_cars where model like(?)";
    private static final String SAVE_SQL = "insert into m_cars " +
            "(model, guarantee_expiration_date, price, dealer_id, user_id, country) "
            + "values (?,?,?,?,?,?)";
    private static final String FIND_ALL_SQL = "select * from m_cars order by id";
    private static final String FIND_BY_ID_SQL = "select * from m_cars where id = ?";
    private static final String UPDATE_SQL = "update m_cars " + "set " + "model = ?, " +
            "guarantee_expiration_date = ?, " + "price = ?, " + "dealer_id = ?, " +
            "user_id = ?, " + "country = ?, " + "where id = ?";
    private static final String DELETE_SQL = "delete * from m_cars where id = ?";


    public static final DatabasePropertiesReader reader = DatabasePropertiesReader.getInstance();

    private Connection createConnection() {
        Connection connection;
        try {
            Class.forName(reader.getProperty(DATABASE_DRIVER_NAME));
            connection = DriverManager.getConnection(reader.getProperty(DATABASE_URL),
                    reader.getProperty(DATABASE_LOGIN),
                    reader.getProperty(DATABASE_PASSWORD));
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded");
        }

        return connection;
    }

    @Override
    public List<Car> search(String query) {
        final String tempQuery = "%" + query + "%";

        List<Car> result = new ArrayList<>();

        try (Connection connection =  createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_SQL)) {
            preparedStatement.setString(1, tempQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result.add(parseResultSet(resultSet));
            } else {
                throw new EntityNotFoundException("Car not found");
            }

            return result;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Car save(Car car) {

        try (Connection connection = createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            PreparedStatement lastInsertId = connection.prepareStatement("SELECT currval('m_cars_id_seq') as last_insert_id;");
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setTimestamp(2, car.getGuarantee_expiration_date());
            preparedStatement.setDouble(3, car.getPrice());
            preparedStatement.setLong(4, car.getUser_id());
            preparedStatement.setLong(5, car.getDealer_id());
            preparedStatement.setString(6, car.getCountry());
            preparedStatement.executeUpdate();

            Long insertedId;
            ResultSet lastIdResultSet = lastInsertId.executeQuery();

            if (lastIdResultSet.next()) {
                insertedId = lastIdResultSet.getLong("last_insert_id");
            } else {
                throw new RuntimeException("We cannot read sequence last value during Car creation");
            }

            return findById(insertedId);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public List<Car> findAll() {
        List<Car> result = new ArrayList<>();

        try (Connection connection = createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(parseResultSet(resultSet));
            }

            return result;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Car findById(Long key) {
        try (Connection connection = createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                return parseResultSet(resultSet);
            } else {
                throw new EntityNotFoundException("Car not found");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Optional<Car> findOne(Long key) {
        return Optional.of(findById(key));
    }

    @Override
    public Car update(Car car) {

        try (Connection connection = createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setString(1, car.getModel());
            preparedStatement.setTimestamp(2, car.getGuarantee_expiration_date());
            preparedStatement.setDouble(3, car.getPrice());
            preparedStatement.setLong(4, car.getUser_id());
            preparedStatement.setLong(5, car.getDealer_id());
            preparedStatement.setString(6, car.getCountry());
            preparedStatement.executeUpdate();

            return findById(car.getId());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Long delete(Car car) {

        try (Connection connection = createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, car.getId());
            int deletedRow = preparedStatement.executeUpdate();

            return (long) deletedRow;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    private Car parseResultSet(ResultSet resultSet) throws SQLException {

        Car car = new Car();
        car.setId(resultSet.getLong(ID));
        car.setModel(resultSet.getString(MODEL));
        car.setGuarantee_expiration_date(resultSet.getTimestamp(GUARANTEE));
        car.setPrice(resultSet.getDouble(PRICE));
        car.setDealer_id(resultSet.getLong(DEALER_ID));
        car.setUser_id(resultSet.getLong(USER_ID));
        car.setCountry(resultSet.getString(COUNTRY));

        return car;
    }
}
