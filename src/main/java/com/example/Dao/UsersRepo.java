package com.example.Dao;

import com.example.Entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by User on 24/04/2017.
 */

public interface UsersRepo extends CrudRepository<User, Integer> {
    public User findUserByEmail(String mail);
}
