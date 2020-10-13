package org.example.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.example.domain.Gender;
import org.example.domain.User;
import org.example.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    public static final String POSTGRES_DRIVER_NAME = "org.postgresql.Driver";
    public static final String DATABASE_URL = "jdbc:postgresql://localhost:";
    public static final int DATABASE_PORT = 5432;
    public static final String DATABASE_NAME = "/webinar_database";
    public static final String DATABASE_LOGIN = "postgres";
    public static final String DATABASE_PASSWORD = "root";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String BIRTH_DATE = "birth_date";
    private static final String GENDER = "gender";
    private static final String CREATED = "created";
    private static final String CHANGED = "changed";
    private static final String WEIGHT = "weight";

    @Override
    public List<User> search(String query) {
        return null;
    }

    @Override
    public User save(User object) {
        return null;
    }

    @Override
    public List<User> findAll() {
        final String findAllQuery = "select * from m_user order by id";

        List<User> result = new ArrayList<>();

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            Class.forName(POSTGRES_DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!!!");
        }

        String jdbcURL = StringUtils.join(DATABASE_URL, DATABASE_PORT, DATABASE_NAME);

        try {
            connection = DriverManager.getConnection(jdbcURL, DATABASE_LOGIN, DATABASE_PASSWORD);
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
        return null;
    }

    @Override
    public Optional<User> findOne(Long key) {
        return Optional.empty();
    }

    @Override
    public User update(User object) {
        return null;
    }

    @Override
    public Long delete(User object) {
        return null;
    }
}
