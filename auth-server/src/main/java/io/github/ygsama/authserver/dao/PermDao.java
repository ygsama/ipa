package io.github.ygsama.authserver.dao;

import io.github.ygsama.authserver.entity.Perm;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermDao {

    @Select("select * from perm")
    List<Perm> findAll();

}
