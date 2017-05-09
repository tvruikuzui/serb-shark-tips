package com.example.Service;

import com.example.Dao.SignalsDao;
import com.example.Dao.UsersDao;
import com.example.Entity.Signal;
import com.example.Entity.TestENUM;
import com.example.Entity.User;
import com.example.Helpers.AndroidPushNotificationsService;
import com.example.Helpers.FirebaseResponse;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by User on 24/04/2017.
 */

@Service
public class UserService {

    private static int performance;

    private static final Logger log = LoggerFactory.getLogger(SignalsService.class);

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private AndroidPushNotificationsService androidPushNotificationsService;

//    @Autowired
//    private SignalsDao signalsDao;

    public String newUser(User user) {
        if(usersDao.newUser(user)){
//            MailchampThread mailchampThread = new MailchampThread();
//            mailchampThread.setUser(user);
//            mailchampThread.start();
            return "ok";
        }
        return "already exsists";
    }

    public String logIn(String mail, String pass) {
        return usersDao.logIn(mail,pass) ? "ok" : "not registerd";
    }

    public Collection<User> getAllUsers() {
        return usersDao.getAllUsers();
    }

    //no args
    public Collection<User> getAllFromRepo() {
        return usersDao.getAllFromRepo();
    }
    //for admin
    public Collection<User> getAllFromRepo(String mail,String pass) {
        return usersDao.getAllFromRepo(mail,pass);
    }

    //regular
    public User.Admin getAdminByMail(String mail) {
        return usersDao.getAdminByMail(mail);
    }

    //admins
    public User getUserByMail(String mail,String pass, String user) {
        return usersDao.getUserByMail(mail,pass,user);
    }

    public String makeAnAdmin(String mail, String pass, String user, int wtich) {
        return usersDao.makeAnAdmin(mail,pass,user,wtich) ? "ok" : "error";
    }

    public int getTrailDays(String mail) {
        return usersDao.getTimeStamp(mail);
    }

    public Collection<TestENUM> testerEnum(){
        return usersDao.enumsTest();
    }

    public static int getPerformace(){
        return performance;
    }

    public String setPerformance(String mail, String pass,int performance) {
        if (usersDao.doYouAdmin(mail,pass) == User.Admin.SUPER_ADMIN && performance > 0 && performance < 100) {
            UserService.performance = performance;
            return "ok";
        }
        return "error";
    }

    public String deleteUserByMail(String mail, String pass, String userm) {
        return usersDao.deleteUserByMail(mail,pass,userm) ? "ok" : "error";
    }

    public void updateUserByMail(String mail, String pass, User user) {
        usersDao.updateUserByMail(mail,pass,user);
    }

    public String refreshedClientToken(String mail, String token) {
        return usersDao.refreshedClientToken(mail,token) ? "ok" : "error";
    }

    public String AddDaysToUser(String mail, String pass, String user, int days,boolean paid) {
        return usersDao.AddDaysToUser(mail,pass,user,days,paid) ? "added": "error";
    }

    public void sendSingleNotificationMessage(String mail, String pass, String message) {
        if (usersDao.doYouAdmin(mail,pass) == User.Admin.SUPER_ADMIN){
            sendNotification(message,usersDao.getAllTokens());
        }
    }

    private void sendNotification(String message, List<String> allTokens) {
        JSONObject body = new JSONObject();
//         JsonArray registration_ids = new JsonArray();
//         body.put("registration_ids", registration_ids);

        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("body", message);
        notification.put("title", "Shark Tips");

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
}
