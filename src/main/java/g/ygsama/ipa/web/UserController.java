package g.ygsama.ipa.web;


import g.ygsama.ipa.entity.User;
import g.ygsama.ipa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/users")     // 通过这里配置使下面的映射都在/users下
public class UserController {

    @Autowired
    private UserService userSerivce;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List getUserList() {
        return  userSerivce.getAllUsers();
    }

//    @RequestMapping(value = "/", method = RequestMethod.POST)
//    public String postUser(@RequestBody User user) {
//        System.out.println(user);
//        userSerivce.create(user.getId(),user.getName(),user.getAge());
//        return "success";
//    }


}