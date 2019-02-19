package g.ygsama.ipa.test_jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(Long id, String name, Integer age) {
        jdbcTemplate.update("insert into user(uid, uname, nick) values(?, ?, ?)", id, name, age);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from user where uid = ?", id);
    }

    @Override
    public List getAllUsers() {
        return jdbcTemplate.queryForList("select * from user");
    }

    @Override
    public void deleteAllUsers() {
        jdbcTemplate.update("delete from user");
    }
}