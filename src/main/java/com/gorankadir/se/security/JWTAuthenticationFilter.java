package com.gorankadir.se.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorankadir.se.entities.Customer;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import static com.gorankadir.se.security.SecurityConstants.EXPIRATION_TIME;
import static com.gorankadir.se.security.SecurityConstants.SECRET;
import static com.gorankadir.se.security.SecurityConstants.TOKEN_PREFIX;
import static com.gorankadir.se.security.SecurityConstants.HEADER_STRING;;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	 private AuthenticationManager authenticationManager;

	    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
	        this.authenticationManager = authenticationManager;
	    }

	    @Override
	    public Authentication attemptAuthentication(HttpServletRequest req,
	                                                HttpServletResponse res) throws AuthenticationException {
	        String username = req.getParameter("username");
			String password = req.getParameter("password");

			return authenticationManager.authenticate(
			        new UsernamePasswordAuthenticationToken(
			                username,
			               password,
			                new ArrayList<>())
			);
	    }

	    @Override
	    protected void successfulAuthentication(HttpServletRequest req,
	                                            HttpServletResponse res,
	                                            FilterChain chain,
	                                            Authentication auth) throws IOException, ServletException {

	        String token = Jwts.builder()
	                .setSubject(((User) auth.getPrincipal()).getUsername())
	                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
	                .compact();
	        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	        super.successfulAuthentication(req, res, chain, auth);
	    }
	}

