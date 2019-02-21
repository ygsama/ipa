package io.github.ygsama.authserver.entity;

import java.io.Serializable;

/**
 * 角色权限表
 */
public class RolePerm implements Serializable {

    private String roleId;
    private String permId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermId() {
        return permId;
    }

    public void setPermId(String permId) {
        this.permId = permId;
    }
}
