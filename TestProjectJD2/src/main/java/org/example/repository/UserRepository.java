package org.example.repository;

import org.example.domain.User;

import java.util.List;

public interface UserRepository extends CrudRepository<Long, User> {
    List<User> search(String query);
}
