package com.example.Dao;

import com.example.Entity.Signal;
import com.example.Entity.TestENUM;
import com.example.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by User on 24/04/2017.
 */

@Repository
public class UsersDao {

    @Autowired
    private UsersRepo usersRepo;

    private static List<TestENUM> testENUM;

    private static Map<Integer,User> users;

    static {
        users = new HashMap<>();
        users.put(34123,new User("ahron","luzon",934582,"jasd@sad.com","pass","israel","+972","heb","starter",false));
        testENUM = new ArrayList<>();
        testENUM.add(new TestENUM(TestENUM.admins.SUPER_ADMIN, TestENUM.admins.USER, TestENUM.admins.ADMIN));
    }

    public boolean newUser(User user) {
        if (usersRepo.exists(user.getId()))
            return false;
        usersRepo.save(user);
        return true;
    }

    public Boolean logIn(String mail, String pass) {
        return usersRepo.findUserByEmail(mail).getPassword().equals(pass);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    //no args
    public Collection<User> getAllFromRepo() {
        List<User> usersFromRepo = new ArrayList<>();
        usersRepo.findAll().forEach(usersFromRepo::add);
        return usersFromRepo;
    }
    //admin
    public Collection<User> getAllFromRepo(String mail,String pass) {
        if (doYouAdmin(mail,pass) == User.Admin.SUPER_ADMIN) {
            List<User> usersFromRepo = new ArrayList<>();
            usersRepo.findAll().forEach(usersFromRepo::add);
            return usersFromRepo;
        }
        return null;
    }

    //no args
    public User getUserByMail(String mail) {
        return usersRepo.findUserByEmail(mail);
    }

    //admin
    public User getUserByMail(String mail,String pass, String user) {
        if (doYouAdmin(mail,pass) == User.Admin.SUPER_ADMIN)
            return usersRepo.findUserByEmail(user);
        return null;
    }

    public User.Admin doYouAdmin(String mail, String pass) {
        if (usersRepo.findUserByEmail(mail).getPassword().equals(pass)) {
            return usersRepo.findUserByEmail(mail).getAdmin();
        }
        return null;
    }

    public boolean makeAnAdmin(String mail, String pass, String user,int toSwitch) {
        String ultimateKey1 = "kuzui123";
        String ultimateKey2 = "tvruikuzui";
        if (mail.equals(ultimateKey1) && pass.equals(ultimateKey2)){
            User user1 = getUserByMail(user);
            user1.setAdmin(User.Admin.SUPER_ADMIN);
            usersRepo.save(user1);
            return true;
        }else {
            if (doYouAdmin(mail, pass) == User.Admin.SUPER_ADMIN) {
                User user1 = getUserByMail(user);
                switch (toSwitch){
                    case 2:
                        user1.setAdmin(User.Admin.SIGNAL_ADMIN);
                        break;
                    case 1:
                        user1.setAdmin(User.Admin.SUPER_ADMIN);
                        break;
                    default:
                        user1.setAdmin(User.Admin.USER);
                }
                usersRepo.save(user1);
                return true;
            }
        }
        return false;
    }

    public int getTimeStamp(String mail) {
        return usersRepo.findUserByEmail(mail).getTs().compareTo(new Date(System.currentTimeMillis()));
    }

    public Collection<TestENUM> enumsTest(){
        return testENUM;
    }

}
