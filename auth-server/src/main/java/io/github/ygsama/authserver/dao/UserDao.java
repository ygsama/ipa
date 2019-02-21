package io.github.ygsama.authserver.dao;

import io.github.ygsama.authserver.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao {

    @Select("")
    List<User> findUser();
}
