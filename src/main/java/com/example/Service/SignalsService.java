package com.example.Service;

import com.example.Dao.SignalsDao;
import com.example.Dao.UsersDao;
import com.example.Entity.Signal;
import com.example.Helpers.AndroidPushNotificationsService;
import com.example.Helpers.FirebaseResponse;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by User on 24/04/2017.
 */

@Service
public class SignalsService {

    private static final Logger log = LoggerFactory.getLogger(SignalsService.class);

    @Autowired
    private SignalsDao signalsDao;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private AndroidPushNotificationsService androidPushNotificationsService;

    public String addSignal(String mail, String pass, Signal signal) {
        if (signalsDao.addSignal(mail,pass,signal)){
            //startSendingNotifications(usersDao.getAllTokens(),signal);
            return "ok";
        }

        return signalsDao.addSignal(mail,pass,signal) ? "ok" : "error";
        //if the signal added seccusses send FCM
    }

    @Async
    private void startSendingNotifications(List<String> allTokens, Signal signal) {
        JSONObject body = new JSONObject();
        // JsonArray registration_ids = new JsonArray();
        // body.put("registration_ids", registration_ids);

        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("body", signal.toString());
        notification.put("title", "New Signal");

        body.put("notification", notification);

        for (String t :
                allTokens) {
            body.put("to", t);

            HttpEntity<String> request = new HttpEntity<>(body.toString());

            CompletableFuture<FirebaseResponse> pushNotification = androidPushNotificationsService.send(request);
            CompletableFuture.allOf(pushNotification).join();

            try {
                FirebaseResponse firebaseResponse = pushNotification.get();
                if (firebaseResponse.getSuccess() == 1) {
                    log.info("push notification sent ok!");
                } else {
                    log.error("error sending push notifications: " + firebaseResponse.toString());
                }
                new ResponseEntity<>(firebaseResponse.toString(), HttpStatus.OK);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            new ResponseEntity<>("the push notification cannot be send.", HttpStatus.BAD_REQUEST);
        }
    }


    public Collection<Signal> getAllSignals() {
        return signalsDao.getAllSignals();
    }
}
