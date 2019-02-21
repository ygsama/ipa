package io.github.ygsama.authserver.dao;


import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RolePermDao {

    @Select("select * from role")
    List<String> findAll();

}
