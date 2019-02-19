package g.ygsama.ipa.service;

import g.ygsama.ipa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 模拟查询返回用户信息
     * @param uname
     * @return
     */
    public User findUserByName(String uname){
        User user = new User();
        user.setUname(uname);
        user.setNick(uname+"NICK");
        user.setPwd("J/ms7qTJtqmysekuY8/v1TAS+VKqXdH5sB7ulXZOWho=");//密码明文是123456
        user.setSalt("wxKYXuTPST5SG0jMQzVPsg==");//加密密码的盐值
        user.setUid("1212");//随机分配一个id
        user.setCreated(new Date().toString());
        return user;
    }


//    public void create(Long id, String name, Integer age) {
//        jdbcTemplate.update("insert into user(uid, uname, nick) values(?, ?, ?)", id, name, age);
//    }
//
//    public void deleteById(Long id) {
//        jdbcTemplate.update("delete from user where uid = ?", id);
//    }
//
    public List getAllUsers() {
        return jdbcTemplate.queryForList("select * from user");
    }
//
//    public void deleteAllUsers() {
//        jdbcTemplate.update("delete from user");
//    }

}
