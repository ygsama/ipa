package io.github.ygsama.authserver.service;

import io.github.ygsama.authserver.dao.UserDao;
import io.github.ygsama.authserver.entity.CustomerUser;
import io.github.ygsama.authserver.entity.Role;
import io.github.ygsama.authserver.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class UserDetailService implements UserDetailsService {


    @Resource
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = new User();
        user.setUname("admin");
        user.setPwd("admin");
        ArrayList<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setRname("worker");
        roles.add(role);
        return new CustomerUser(user,roles);
    }
}
