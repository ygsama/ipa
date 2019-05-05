package io.github.ygsama.securitydemo.dao;

import io.github.ygsama.securitydemo.entity.Perm;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermDao {

    @Select("select * from sys_permission ")
    List<Perm> findAll();

}
