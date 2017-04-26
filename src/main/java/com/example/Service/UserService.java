package com.example.Service;

import com.example.Dao.SignalsDao;
import com.example.Dao.UsersDao;
import com.example.Entity.Signal;
import com.example.Entity.TestENUM;
import com.example.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by User on 24/04/2017.
 */

@Service
public class UserService {

    private static int performance;

    @Autowired
    private UsersDao usersDao;

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
    public User getUserByMail(String mail) {
        return usersDao.getUserByMail(mail);
    }

    //admins
    public User getUserByMail(String mail,String pass, String user) {
        return usersDao.getUserByMail(mail,pass,user);
    }

    public String makeAnAdmin(String mail, String pass, String user) {
        return usersDao.makeAnAdmin(mail,pass,user) ? "ok" : "error";
    }

    public int getTrailDays(String mail) {
        return usersDao.getTimeStamp(mail) + 14;
    }

    public Collection<TestENUM> testerEnum(){
        return usersDao.enumsTest();
    }

    public static int getPerformace(){
        return performance;
    }

    public String setPerformance(String mail, String pass,int performance) {
        if (usersDao.doYouAdmin(mail,pass) && performance > 0 && performance < 100) {
            UserService.performance = performance;
            return "ok";
        }
        return "error";
    }
}
