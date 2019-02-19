package g.ygsama.ipa.test_shiro.entity;

import java.io.Serializable;

/**
 * 用户角色表
 */
public class UserRole implements Serializable {

    private String userId;
    private String roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
