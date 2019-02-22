package io.github.ygsama.securitydemo.dao;

import io.github.ygsama.securitydemo.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao {

    @Select("")
    List<User> findUser();
}
