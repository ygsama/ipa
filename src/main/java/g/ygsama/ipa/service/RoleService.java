package g.ygsama.ipa.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class RoleService {

    /**
     * 模拟根据用户id查询返回用户的所有角色，实际查询语句参考：
     * SELECT r.rval FROM role r, user_role ur
     * WHERE r.rid = ur.role_id AND ur.user_id = #{userId}
     * @param uid
     * @return
     */
    public Set<String> getRolesByUserId(String uid){
        Set<String> roles = new HashSet<>();
        // 查询后得到角色
        roles.add("worker");
        roles.add("manager");
        return roles;
    }

}
