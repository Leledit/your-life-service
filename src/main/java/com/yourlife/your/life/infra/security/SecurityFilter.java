package com.yourlife.your.life.infra.security;

import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.entity.user.UserAuth;
import com.yourlife.your.life.repository.user.UserRepository;
import com.yourlife.your.life.utils.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.swing.text.Style;
import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);

        if(tokenJWT != null){
            var subject = tokenUtils.getSubjects((tokenJWT));

            Optional<User> user =  userRepository.findById(subject);

            UserAuth userAuth = modelMapper.map(user,UserAuth.class);
            var authentication = new UsernamePasswordAuthenticationToken(userAuth,null,userAuth.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request,response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null){
            return  authorizationHeader.replace("Bearer","").trim();
        }

        return  null;
    }
}
