package com.gmail.stepan1983.config;

import com.gmail.stepan1983.Service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

//@Configuration
//@EnableWebSecurity
//@ComponentScan("com.gmail.stepan1983")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }

//    @Bean
//    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
//        return new MySimpleUrlAuthenticationSuccessHandler();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
//                .antMatchers("/").hasAnyRole("USER", "ADMIN")
//                .antMatchers("/adminPage").hasRole("ADMIN")
                .antMatchers("/register","/adminPage","/","/orders","/static/**").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/unauthorized")
                .and().
                httpBasic();
    }

    @Bean
    public AuthenticationEntryPoint entryPoint() {
        BasicAuthenticationEntryPoint point = new BasicAuthenticationEntryPoint();
        point.setRealmName("Geo");
        return point;
    }

}
