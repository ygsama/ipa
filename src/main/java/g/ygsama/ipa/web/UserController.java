package g.ygsama.ipa.web;


import com.alibaba.fastjson.JSONArray;
import g.ygsama.ipa.domain.User;
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

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String postUser(@RequestBody User user) {
        // 处理"/users/"的POST请求，用来创建User
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        System.out.println(user);
        userSerivce.create(user.getId(),user.getName(),user.getAge());
        return "success";
    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public User getUser(@PathVariable Long id) {
//        // 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
//        // url中的id可通过@PathVariable绑定到函数的参数中
//        return users.get(id);
//    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    public String putUser(@PathVariable Long id, @ModelAttribute User user) {
//        // 处理"/users/{id}"的PUT请求，用来更新User信息
//        User u = users.get(id);
//        u.setName(user.getName());
//        u.setAge(user.getAge());
//        users.put(id, u);
//        return "success";
//    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        userSerivce.deleteById(id);
        return "success";
    }

}