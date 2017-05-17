package com.example.Helpers;


import com.example.Dao.SignalsRepo;
import com.example.Entity.Signal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static long ONCE_PER_DAY = 1000*60*60*24;

    @Scheduled(fixedRate = ONCE_PER_DAY)
    public void removeSignalsAfter30Days(){
        for (Signal s :
                signalsRepo.findAll()) {
            if (s.getTs().after(new Date(s.getTs().getTime() + 2592000000L))) {
                signalsRepo.delete(s);
            }
        }
    }


}
