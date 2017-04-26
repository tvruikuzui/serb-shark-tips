package com.example.Dao;

import com.example.Entity.Signal;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by User on 24/04/2017.
 */

public interface SignalsRepo extends CrudRepository<Signal,Integer> {
}
