package g.ygsama.ipa.service;

import g.ygsama.ipa.entity.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
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

    /**
     * 添加用户
     */
    public void addUser(String id, String name, String pwd) {

//        DefaultHashService hashService = new DefaultHashService(); //默认算法 SHA-512
//        hashService.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
//        hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐，默认无
//        hashService.setGeneratePublicSalt(true);//是否生成公盐，默认 false
//        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个
//        hashService.setHashIterations(2); //生成 Hash 值的迭代次数
//        HashRequest request = new HashRequest.Builder()
//                .setAlgorithmName("MD5")
//                .setSource(ByteSource.Util.bytes("hello"))
//                .setSalt(ByteSource.Util.bytes("123"))
//                .setIterations(2).build();
//        String hex = hashService.computeHash(request).toHex();

//        jdbcTemplate.update("insert into user(uid, uname, nick) values(?, ?, ?)", id, name, age);
    }
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
