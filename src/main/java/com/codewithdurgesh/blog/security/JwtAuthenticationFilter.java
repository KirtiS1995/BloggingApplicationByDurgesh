package com.codewithdurgesh.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		//1. get Token 
		String requestToken=request.getHeader("Authorization");
		
		//token strts with = Bearer 2345623sgdf
		System.out.println(requestToken);
		
		String username=null;
		String token=null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
			
			token=requestToken.substring(7);
			try
			{
				username = this.jwtTokenHelper.getUsernameFromToken(token);
			}
			catch (IllegalArgumentException e) {
//					System.out.println("Unable to get JWT token");
					log.warn("Unable to get JWT token");
			}
			catch (ExpiredJwtException e) {
				log.warn("JWT token has expired ");
			}
			catch (MalformedJwtException e) {
				log.warn(" Invalid JWT  ");
			}
		}
		else
		{
			log.warn("JWT Token doesnot starts with bearer.");
		}
		
		//once we get token .then validate
		if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtTokenHelper.validateToken(token, userDetails))
			{
				//if validate
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new 
						UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}
			else
			{
				log.warn("Invalid JWT token ");
			}
			
		}
		else
		{
			log.warn("Username is null or context is not null ");
		}
		
		filterChain.doFilter(request, response);
	}

}
