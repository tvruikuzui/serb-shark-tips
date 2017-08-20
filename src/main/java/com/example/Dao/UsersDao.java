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
        users.put(34123,new User("kjhf@gmail.com",054262354,"ahron","luzon","pass","israel","+972","heb","starter","some token"));
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
    private User getUserByMail(String mail) {
        return usersRepo.findUserByEmail(mail);
    }

    public User.Admin getAdminByMail(String mail) {
        return usersRepo.findUserByEmail(mail).getAdmin();
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

    public List<String> getAllTokens(){
        List<String> tokens = new ArrayList<>();
        for (User u :
                getAllFromRepo()) {
            if (!u.getToken().equals("0"))
                tokens.add(u.getToken());
        }
        return tokens;
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
        User u;
        if (!(u = usersRepo.findUserByEmail(mail)).isPaid()){
            //return u.getTs().compareTo(new Date(System.currentTimeMillis() - (86400000 * u.getAddTimeToUser())));
            int daysSinceRegister = (int) ((u.getTs().getTime() - new Date().getTime()) / 86400000L ) + u.getAddTimeToUser();
            if (daysSinceRegister < 0){
                u.setToken("0");
                daysSinceRegister = -1;
            }
            return daysSinceRegister;
        }else{
            return -404;
        }
        //return usersRepo.findUserByEmail(mail).getTs().compareTo(new Date(System.currentTimeMillis()));
    }

    public Collection<TestENUM> enumsTest(){
        return testENUM;
    }

    public Boolean deleteUserByMail(String mail, String pass, String userm) {
        if (doYouAdmin(mail,pass) == User.Admin.SUPER_ADMIN){
            usersRepo.delete(getUserByMail(mail).getId());
            return true;
        }
        return false;
    }

    public void updateUserByMail(String mail, String pass, User user) {
        if (doYouAdmin(mail,pass) == User.Admin.SUPER_ADMIN){
            User t = getUserByMail(mail);
            t.setCountry(user.getCountry());
            t.setCountryCode(user.getCountryCode());
            t.setName(user.getName());
            t.setLangSpeak(user.getLangSpeak());
            t.setLastName(user.getLastName());
            t.setTradeLvl(user.getTradeLvl());
            t.setPaid(user.isPaid());
            t.setPassword(user.getPassword());
            t.setPhoneNumber(user.getPhoneNumber());
            usersRepo.save(t);
        }
    }


    public boolean refreshedClientToken(String mail, String token) {
        User u;
        if ((u = usersRepo.findUserByEmail(mail)) != null){
            u.setToken(token);
            usersRepo.save(u);
            return true;
        }
        //User u = usersRepo.findUserByEmail(mail);

        return false;
    }

    public boolean AddDaysToUser(String mail, String pass, String user, int days,boolean paid, boolean add) {
        if (doYouAdmin(mail, pass) == User.Admin.SUPER_ADMIN){
            User u = getUserByMail(user);
            if (add) {
                u.setAddTimeToUser(u.getAddTimeToUser() + days);
            }else {
                u.setAddTimeToUser(u.getAddTimeToUser() - days);
            }
            u.setPaid(paid);
            usersRepo.save(u);
            return true;
        }
        return false;
    }
    
    public ArrayList<String> getEmails (){
        ArrayList<String> send = new ArrayList<>();
        for (User u :
                usersRepo.findAll()) {
            //todo:fix here
            if (((u.getTs().getTime() - new Date().getTime()) / 86400000L ) + u.getAddTimeToUser() > 0){
                send.add(u.getEmail());
            }else {
                u.setToken("0");
                usersRepo.save(u);
            }
        }
        return send;
    }
}
