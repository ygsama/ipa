package io.github.ygsama.securitydemo.dao;


import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RolePermDao {

    @Select("select * from role")
    List<String> findAll();

}
