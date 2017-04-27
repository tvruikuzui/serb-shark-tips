package com.example.Controller;

import com.example.Entity.Signal;
import com.example.Entity.User;
import com.example.Service.SignalsService;
import com.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by User on 24/04/2017.
 */

@RestController
@RequestMapping(value = "admin/{mail:.+}/{pass}")
public class AdminCondroller {

    @Autowired
    private UserService userService;

    @Autowired
    private SignalsService signalsService;

    //working
    @RequestMapping(method = RequestMethod.GET)
    public Collection<User> getAllFromRepo(@PathVariable("mail") String mail, @PathVariable("pass")String pass){
        return userService.getAllFromRepo(mail,pass);
    }

    //working
    @RequestMapping(method = RequestMethod.PUT,value = "{user:.+}/{i}")
    public String makeAnAdmin(@PathVariable("mail") String mail,@PathVariable("pass") String pass, @PathVariable("user") String user, @PathVariable("i") int witch){
        return userService.makeAnAdmin(mail,pass,user,witch);
    }

    //working
    @RequestMapping(method = RequestMethod.GET,value = "/{user:.+}")
    public User getUserByMail(@PathVariable("mail") String mail,@PathVariable("pass") String pass,@PathVariable("user") String userm){
        return userService.getUserByMail(mail,pass,userm);
    }

    //working
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addSignal(@PathVariable("mail") String mail,@PathVariable("pass") String pass,@RequestBody Signal signal){
        return signalsService.addSignal(mail,pass,signal);
    }

    //working
    @RequestMapping(method = RequestMethod.PUT, value = "performance/{p}")
    public String setPerformance(@PathVariable("mail") String mail,@PathVariable("pass") String pass,@PathVariable("p") int performance){
        return userService.setPerformance(mail,pass,performance);
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/{user:.+}")
    public String deleteUserByMail(@PathVariable("mail") String mail,@PathVariable("pass") String pass,@PathVariable("user") String userm){
        return userService.deleteUserByMail(mail,pass,userm);
    }

    @RequestMapping(method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE,value = "/update_user/")
    public void updateUserByMail(@PathVariable("mail") String mail,@PathVariable("pass") String pass,@RequestBody User user){
        userService.updateUserByMail(mail,pass,user);
    }
}
