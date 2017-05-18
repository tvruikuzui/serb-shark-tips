package com.example.Dao;

import com.example.Entity.Signal;
import com.example.Entity.User;
import com.example.Helpers.CheckEveryDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 24/04/2017.
 */

@Repository
public class SignalsDao {

    private static List<Signal> testS;

    static {
        testS = new ArrayList<>();
        testS.add(new Signal(false,23,"currncy",false,234.2,321.4,423.1,432.2,321.7,"some note","name of sl"));
        //CheckEveryDay.startTask();
    }

    @Autowired
    private SignalsRepo signalsRepo;

    @Autowired
    private UsersDao usersDao;

    public boolean addSignal(String mail, String pass, Signal signal) {
        if (usersDao.doYouAdmin(mail,pass) == User.Admin.SIGNAL_ADMIN || usersDao.doYouAdmin(mail,pass) == User.Admin.SUPER_ADMIN) {
            signalsRepo.save(signal);
            return true;
        }
        return false;
    }

    public Collection<Signal> getAllSignals(){
        List<Signal> signals = new ArrayList<>();
        signalsRepo.findAll().forEach(signals::add);
        return signals;
    }
}
