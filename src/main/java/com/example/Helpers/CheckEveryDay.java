package com.example.Helpers;


import com.example.Dao.SignalsRepo;
import com.example.Dao.UsersRepo;
import com.example.Entity.Signal;
import com.example.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by User on 17/05/2017.
 */
@Component
public class CheckEveryDay {

    @Autowired
    private SignalsRepo signalsRepo;

    @Autowired
    private UsersRepo usersRepo;

    private final static long ONCE_PER_DAY = 1000*60*60*24;

    @Scheduled(fixedRate = ONCE_PER_DAY)
    public void removeSignalsAfter30Days(){
        for (Signal s :
                signalsRepo.findAll()) {
            if (s.getTs().after(new Date(s.getTs().getTime() + 2592000000L))) {
                signalsRepo.delete(s);
            }
        }
//        for (User u :
//                usersRepo.findAll()) {
//            if (u.getTs())
//
//        }
    }


}
