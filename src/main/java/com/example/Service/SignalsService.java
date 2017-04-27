package com.example.Service;

import com.example.Dao.SignalsDao;
import com.example.Entity.Signal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by User on 24/04/2017.
 */

@Service
public class SignalsService {

    @Autowired
    private SignalsDao signalsDao;

    public String addSignal(String mail, String pass, Signal signal) {
        return signalsDao.addSignal(mail,pass,signal) ? "ok" : "error";
        //if the signal added seccusses send FCM
    }


    public Collection<Signal> getAllSignals() {
        return signalsDao.getAllSignals();
    }
}
