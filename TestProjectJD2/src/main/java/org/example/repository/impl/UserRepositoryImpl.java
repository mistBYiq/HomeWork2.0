package org.example.repository.impl;

import org.example.domain.Gender;
import org.example.domain.User;
import org.example.exception.EntityNotFoundException;
import org.example.repository.UserRepository;
import org.example.util.DatabasePropertiesReader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.util.DatabasePropertiesReader.*;


public class UserRepositoryImpl implements UserRepository {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String BIRTH_DATE = "birth_date";
    private static final String GENDER = "gender";
    private static final String CREATED = "created";
    private static final String CHANGED = "changed";
    private static final String WEIGHT = "weight";

    public static final DatabasePropertiesReader reader = DatabasePropertiesReader.getInstance();

    @Override
    public List<User> search(String query) {
        final String searchQuery = "select * from m_users where name like(?)";
        final String tempQuery = "%" + query + "%";

        List<User> result = new ArrayList<>();

        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            Class.forName(reader.getProperty(DATABASE_DRIVER_NAME));
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded");
        }

        try {
            connection = DriverManager.getConnection(reader.getProperty(DATABASE_URL),
                    reader.getProperty(DATABASE_LOGIN),
                    reader.getProperty(DATABASE_PASSWORD));
            preparedStatement = connection.prepareStatement(searchQuery);
            preparedStatement.setString(1, tempQuery);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result.add(parseResultSet(resultSet));
            } else {
                throw new EntityNotFoundException("User not found");
            }

            return result;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public User save(User user) {

        final String saveQuery = "insert into m_users (name, surname, birth_date, gender, created, changed, weight) " +
                "values (?,?,?,?,?,?,?)";

        Connection connection;
        PreparedStatement preparedStatement;

        try {
            Class.forName(reader.getProperty(DATABASE_DRIVER_NAME));
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded");
        }

        try {
            connection = DriverManager.getConnection(reader.getProperty(DATABASE_URL),
                    reader.getProperty(DATABASE_LOGIN),
                    reader.getProperty(DATABASE_PASSWORD));
            preparedStatement = connection.prepareStatement(saveQuery);
            PreparedStatement lastInsertId = connection.prepareStatement("SELECT currval('m_user_id_seq') as last_insert_id;");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setDate(3, new Date(user.getBirthDate().getTime()));
            preparedStatement.setString(4, user.getGender().name());
            preparedStatement.setTimestamp(5, user.getCreated());
            preparedStatement.setTimestamp(6, user.getChanged());
            preparedStatement.setFloat(7, user.getWeight());
            preparedStatement.executeUpdate();

            Long insertedId;
            ResultSet lastIdResultSet = lastInsertId.executeQuery();

            if (lastIdResultSet.next()) {
                insertedId = lastIdResultSet.getLong("last_insert_id");
            } else {
                throw new RuntimeException("We cannot read sequence last value during User creation");
            }

            return findById(insertedId);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public List<User> findAll() {
        final String findAllQuery = "select * from m_user order by id";

        List<User> result = new ArrayList<>();

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            Class.forName(reader.getProperty(DATABASE_DRIVER_NAME));
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!!!");
        }

        try {
            connection = DriverManager.getConnection(reader.getProperty(DATABASE_URL),
                    reader.getProperty(DATABASE_LOGIN),
                    reader.getProperty(DATABASE_PASSWORD));
            statement = connection.createStatement();
            resultSet = statement.executeQuery(findAllQuery);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(ID));
                user.setName(resultSet.getString(NAME));
                user.setSurname(resultSet.getString(SURNAME));
                user.setBirthDate(resultSet.getDate(BIRTH_DATE));
                user.setGender(Gender.valueOf(resultSet.getString(GENDER)));
                user.setCreated(resultSet.getTimestamp(CREATED));
                user.setChanged(resultSet.getTimestamp(CHANGED));
                user.setWeight(resultSet.getFloat(WEIGHT));

                result.add(user);
            }
            return result;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!!!");
        }
    }

    @Override
    public User findById(Long key) {
        final String findByIdQuery = "select * from m_users where id = ?";

        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet rs;

        try {
            Class.forName(reader.getProperty(DATABASE_DRIVER_NAME));
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded");
        }

        try {
            connection = DriverManager.getConnection(reader.getProperty(DATABASE_URL),
                    reader.getProperty(DATABASE_LOGIN),
                    reader.getProperty(DATABASE_PASSWORD));
            preparedStatement = connection.prepareStatement(findByIdQuery);
            preparedStatement.setLong(1, key);

            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return parseResultSet(rs);
            } else {
                throw new EntityNotFoundException("User with ID: "+ key + "not found");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Optional<User> findOne(Long key) {
        return Optional.of(findById(key));
    }

    @Override
    public User update(User user) {

        final String updateQuery = "update m_users " +
                "set " +
                "name = ?, " +
                "surname = ?, " +
                "birth_date = ?, " +
                "gender = ?, " +
                "created = ?, " +
                "changed = ?, " +
                "weight = ? " +
                "where id = ?";

        Connection connection;
        PreparedStatement preparedStatement;

        try {
            Class.forName(reader.getProperty(DATABASE_DRIVER_NAME));
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded");
        }

        try {
            connection = DriverManager.getConnection(reader.getProperty(DATABASE_URL),
                    reader.getProperty(DATABASE_LOGIN),
                    reader.getProperty(DATABASE_PASSWORD));
            preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setDate(3, new Date(user.getBirthDate().getTime()));
            preparedStatement.setString(4, user.getGender().name());
            preparedStatement.setTimestamp(5, user.getCreated());
            preparedStatement.setTimestamp(6, user.getChanged());
            preparedStatement.setFloat(7, user.getWeight());
            preparedStatement.setLong(8, user.getId());

            preparedStatement.executeUpdate();
            return findById(user.getId());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Long delete(User user) {
        final String deleteQuery = "delete * from m_users where id = ?";

        Connection connection;
        PreparedStatement preparedStatement;

        try {
            Class.forName(reader.getProperty(DATABASE_DRIVER_NAME));
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded");
        }

        try {
            connection = DriverManager.getConnection(reader.getProperty(DATABASE_URL),
                    reader.getProperty(DATABASE_LOGIN),
                    reader.getProperty(DATABASE_PASSWORD));
            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setLong(1, user.getId());

            int deletedRow = preparedStatement.executeUpdate();
            return (long) deletedRow;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    private User parseResultSet(ResultSet rs) throws SQLException {
        User user = new User();

        user.setId(rs.getLong(ID));
        user.setName(rs.getString(NAME));
        user.setSurname(rs.getString(SURNAME));
        user.setBirthDate(rs.getDate(BIRTH_DATE));
        user.setGender(Gender.valueOf(rs.getString(GENDER)));
        user.setCreated(rs.getTimestamp(CREATED));
        user.setChanged(rs.getTimestamp(CHANGED));
        user.setWeight(rs.getFloat(WEIGHT));
        return user;
    }
}