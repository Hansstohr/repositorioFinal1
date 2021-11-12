package com.CalificAR.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CalificArApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalificArApplication.class, args);
    }

    /*
    @Autowired
    private LoginServicio loginServicio;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(loginServicio)
                .passwordEncoder(new BCryptPasswordEncoder());

    }
    */
}
