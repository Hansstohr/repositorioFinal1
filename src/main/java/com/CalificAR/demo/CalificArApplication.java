package com.CalificAR.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.CalificAR.demo.Servicios.LoginServicio;

@SpringBootApplication
public class CalificArApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalificArApplication.class, args);
    }

    @Autowired
    private LoginServicio loginServicio;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(loginServicio)
                .passwordEncoder(new BCryptPasswordEncoder());

    }

}
