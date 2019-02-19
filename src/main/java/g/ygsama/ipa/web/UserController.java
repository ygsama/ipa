package g.ygsama.ipa.web;


import g.ygsama.ipa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List getUserList() {
        return  userService.getAllUsers();
    }

//    @RequestMapping(value = "/", method = RequestMethod.POST)
//    public String postUser(@RequestBody User user) {
//        System.out.println(user);
//        userService.create(user.getId(),user.getName(),user.getAge());
//        return "success";
//    }


}