package com.gorankadir.se.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gorankadir.se.entities.Customer;
import com.gorankadir.se.entities.Roles;
import com.gorankadir.se.repository.CustomerRepository;
import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private CustomerRepository customerRepository;

    public UserDetailsServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException(username);
        }
        
        Collection<GrantedAuthority> roles = new ArrayList<>();
        
        for(Roles role : customer.getRole()){
        	roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        
        return new User(customer.getUsername(), customer.getPassword(), roles);
    }
}

