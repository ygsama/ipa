package g.ygsama.ipa.service;

import java.util.List;

public interface UserService {

    /**
     * 新增一个用户
     * @param name
     * @param age
     */
    void create(Long id, String name, Integer age);

    /**
     * 根据name删除一个用户高
     * @param name
     */
    void deleteById(Long name);

    /**
     * 获取用户List
     */
    List getAllUsers();

    /**
     * 删除所有用户
     */
    void deleteAllUsers();

}