package io.github.ygsama.springsecurity.config.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;

/**
 * 用户登录的service实现类 <br>
 * 框架的默认实现是{@link JdbcDaoImpl} <br>
 *
 * @author 杨光
 */
@Slf4j
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("".equals(username)) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPassword();
    }
}
