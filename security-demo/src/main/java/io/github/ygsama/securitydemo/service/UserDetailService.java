package io.github.ygsama.securitydemo.service;

import io.github.ygsama.securitydemo.dao.UserDao;
import io.github.ygsama.securitydemo.entity.CustomerUser;
import io.github.ygsama.securitydemo.entity.Role;
import io.github.ygsama.securitydemo.entity.User;
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
        Role role1 = new Role();
        role.setRname("worker");
        role1.setRname("admin");
        roles.add(role);
        roles.add(role1);
        return new CustomerUser(user,roles);
    }
}
