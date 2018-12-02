package com.gmail.stepan1983.Service;

import com.gmail.stepan1983.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ClientService clientService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Client client=clientService.getByLogin(login);
        if (client==null){
            throw new UsernameNotFoundException("client with "+login+" login not found");
        }

        Set<GrantedAuthority> roles = new HashSet<>();

        roles.add(new SimpleGrantedAuthority(client.getRole().toString()));

        return new User(client.getLogin(), client.getPassword(), roles);
    }
}
