package io.github.ygsama.oauth2server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * spring security 配置文件
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService loginUserDetailsService;

    @Autowired
    public WebSecurityConfig(UserDetailsService loginUserDetailsService) {
        this.loginUserDetailsService = loginUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        String idForEncode = "bcrypt";
//        Map<String, PasswordEncoder> encoders = new HashMap<>();
//        encoders.put(idForEncode, new BCryptPasswordEncoder());
//        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
//        encoders.put("scrypt", new SCryptPasswordEncoder());
//        return new DelegatingPasswordEncoder(idForEncode, encoders);
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public static void main(String[] args) {
        // 计算 BCryptPasswordEncoder 密文
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123456"));
        System.out.println(bCryptPasswordEncoder.encode("12345678"));
        System.out.println(bCryptPasswordEncoder.encode("password"));
        System.out.println(bCryptPasswordEncoder.matches("989df7b4501d2f2b7f4e3f4e06801f3b", "$2a$10$AEVbedAn1ClYKZu49zdb..rjCMfdZrISpT90C1Sl61cVu/nq6hU7i"));

    }

}
