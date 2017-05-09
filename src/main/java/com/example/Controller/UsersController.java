package com.example.Controller;

import com.example.Entity.Signal;
import com.example.Entity.TestENUM;
import com.example.Entity.User;
import com.example.Service.SignalsService;
import com.example.Service.UserService;
import org.apache.naming.StringManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by User on 24/04/2017.
 */

@RestController(value = "/")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private SignalsService signalsService;

    //working
    @RequestMapping(value = "{mail:.+}/{pass}",method = RequestMethod.GET)
    public String logIn(@PathVariable("mail") String mail,@PathVariable("pass") String pass){
        return userService.logIn(mail,pass);
    }

    //working
    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newUser(@RequestBody User user){
        return userService.newUser(user);
    }

    //static for testing
    @RequestMapping(method = RequestMethod.GET,value = "static/")
    public Collection<User> getAllUsers (){
        return userService.getAllUsers();
    }

    //from DB shorten
    @RequestMapping(method = RequestMethod.GET)
    public Collection<User> getAllFromRepo(){
        return userService.getAllFromRepo();
    }

    //this returns witch admin is the user
    @RequestMapping(method = RequestMethod.GET,value = "/{mail:.+}")
    public int getUserByMail(@PathVariable("mail") String mail){
        switch (userService.getAdminByMail(mail)){
            case SUPER_ADMIN:
                return 1;
            case SIGNAL_ADMIN:
                return 2;
            default:
                return 3;
        }
    }

    //working
    @RequestMapping(method = RequestMethod.GET,value = "/ts/{mail:.+}")
    public int getTrailDays(@PathVariable("mail") String mail){
        return userService.getTrailDays(mail);
    }

    //test ENUMS
    @RequestMapping(method = RequestMethod.GET,value = "/enums")
    public Collection<TestENUM> getEnums(){
        return userService.testerEnum();
    }

    @RequestMapping(method = RequestMethod.GET,value = "signals/")
    public Collection<Signal> getAllSignals(){
        return signalsService.getAllSignals();
    }

    @RequestMapping(method = RequestMethod.GET,value = "performace/")
    public static int getPerformace(){
        return UserService.getPerformace();
    }

    @RequestMapping(method = RequestMethod.POST, value = "token/{mail:.+}")
    public String refreshedClientToken(@PathVariable("mail") String mail,@RequestBody String token){
        return userService.refreshedClientToken(mail,token);
    }
}
