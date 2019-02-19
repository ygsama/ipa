package g.ygsama.ipa.entity;

/**
 * 角色
 */
public class Role {

    private String rid;     // 角色id
    private String rname;   // 角色名，用于显示
    private String rdesc;   // 角色描述
    private String rvalue;    // 角色值，用于权限判断
    private String created;   // 创建时间
    private String updated;   // 修改时间


    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRdesc() {
        return rdesc;
    }

    public void setRdesc(String rdesc) {
        this.rdesc = rdesc;
    }

    public String getRvalue() {
        return rvalue;
    }

    public void setRvalue(String rvalue) {
        this.rvalue = rvalue;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }


}
