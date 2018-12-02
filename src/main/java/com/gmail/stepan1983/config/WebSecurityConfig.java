package com.gmail.stepan1983.config;

import com.gmail.stepan1983.Service.UserDetailsServiceImpl;
import com.gmail.stepan1983.config.jwt.JwtAuthEntryPoint;
import com.gmail.stepan1983.config.jwt.JwtAuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(
//		prePostEnabled = true
//)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable().
//                authorizeRequests()
//                .antMatchers("/api/auth/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.cors().and().csrf().disable()
                .authorizeRequests()
//                .antMatchers("/").hasAnyRole("USER", "ADMIN")
                .antMatchers("/orders").hasAnyRole("ADMIN","USER","MANAGER")
                .antMatchers("/userPage","/usersCount","/addBooks","countOrdersByParam","/createNewBookItem",
                        "/createNewCategory","/createNewPublisher","/deleteBookItem","/deleteOrder","/deleteUser",
                        "/getTasks","/orderCount","/usersCount","/getUsers","/saveBookItem","/saveOrder","/tasks").hasAnyRole("ADMIN","MANAGER")
                .antMatchers("/","/static/**","/bookItems","/bookItemsByParam","/createNewUser",
                        "/getBookParameters","/cover/{coverId}","/photo/{photoId}","/rates","/storageBook","/bookCount",
                        "/submitOrder").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/unauthorized")
                .and().
                httpBasic();
        
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}