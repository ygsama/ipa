package io.github.ygsama.oauth2server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailService implements UserDetailsService {


    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Resource
//    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = new User();
        user.setUname("admin");
        user.setPwd("admin");
//        user.setPwd(passwordEncoder.encode("admin"));

        ArrayList<Role> roles = new ArrayList<>();
        Role role = new Role();
        Role role1 = new Role();
        role.setRname("user");
        role1.setRname("admin");
        roles.add(role);
        roles.add(role1);
        return new LoginUser(user,roles);
    }
}
