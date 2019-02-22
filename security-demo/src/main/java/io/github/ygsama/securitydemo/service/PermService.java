package io.github.ygsama.securitydemo.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PermService {

    /**
     * 模拟根据用户id查询返回用户的所有权限，实际查询语句参考：
     * SELECT p.pval FROM perm p, role_perm rp, user_role ur
     * WHERE p.pid = rp.perm_id AND ur.role_id = rp.role_id
     * AND ur.user_id = #{userId}
     * @param uid
     * @return
     */
    public Set<String> getPermsByUserId(String uid){
        Set<String> perms = new HashSet<>();

        perms.add("user:select");
//        perms.add("user:*");
//        perms.add("sys:*");

        return perms;
    }

}
