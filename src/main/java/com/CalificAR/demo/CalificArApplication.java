package com.CalificAR.demo;

import com.CalificAR.demo.Servicios.AlumnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CalificArApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalificArApplication.class, args);
    }

    @Autowired
    private AlumnoServicio alumnoServicio;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(alumnoServicio)
                .passwordEncoder(new BCryptPasswordEncoder());

    }

}
