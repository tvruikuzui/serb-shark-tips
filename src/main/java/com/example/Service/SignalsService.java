package com.example.Service;

import com.example.Dao.SignalsDao;
import com.example.Dao.UsersDao;
import com.example.Entity.Signal;
import com.example.Helpers.AndroidPushNotificationsService;
import com.example.Helpers.FirebaseResponse;
import com.mysql.cj.xdevapi.JsonArray;
import org.json.simple.JSONObject;
import org.json.JSONException;
//import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    private MailService mailService;

    @Autowired
    public SignalsService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    private AndroidPushNotificationsService androidPushNotificationsService;

    public String addSignal(String mail, String pass, Signal signal) {
        boolean[] res = signalsDao.addSignal(mail,pass,signal);
        if (res[1]){
            startSendMails(usersDao.getEmails(),signal.toString(),res[0]);
            startSendingNotifications(usersDao.getAllTokens(),signal,res[0]);
            return "ok";
        }

        return "error";
        //if the signal added seccusses send FCM
    }

    private void startSendMails(ArrayList<String> emails, String s, boolean re) {
        String update = re ? "Update Signal" : "New Signal";
        String body = "";
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(s);
            body += "currency - " + jsonObject.getString("currency")+"\n";
            body += "buy stop - " + jsonObject.getString("sellStop")+"\n";
            body += "SL - " + jsonObject.getString("sl")+"\n";
            body += "TP1 - " + jsonObject.getString("tp1")+"\n";
            body += "TP2 - " + jsonObject.getString("tp2");

            mailService.sendMail(emails,body,update);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Async
    private void startSendingNotifications(List<String> allTokens, Signal signal, boolean update) {
        JSONObject body = new JSONObject();
//         JsonArray registration_ids = new JsonArray();
//         body.put("registration_ids", registration_ids);

        body.put("priority", "high");

        String title = "New Signal";
        if (update){
            title = "Update Signal";
        }

        JSONObject notification = new JSONObject();
        notification.put("body", signal.toString());
        notification.put("title", title);

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
