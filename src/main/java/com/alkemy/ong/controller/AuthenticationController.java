package com.alkemy.ong.controller;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.alkemy.ong.dto.AuthenticactionAuthDto;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.model.Users;
import com.alkemy.ong.repository.UsersRspository;
import com.alkemy.ong.service.impl.EmailServiceImpl;
import com.alkemy.ong.service.impl.UserDetailsServiceImpl;
import com.alkemy.ong.service.impl.UsersServiceImpl;
import com.alkemy.ong.util.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alkemy.ong.dto.AuthenticationRequestDto;
import com.alkemy.ong.security.jwt.JwtUtils;
import org.springframework.web.server.ResponseStatusException;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(AuthenticationController.AUTH)
public class AuthenticationController {

    protected static final String AUTH = "/auth";
    private static final String LOGIN = "/login";
    private static final String MYINFO = "/me";

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final UsersRspository usersRspository;
    private final MessageHandler messageHandler;
    private final EmailServiceImpl emailServiceImpl;

    @Autowired
    private UsersServiceImpl usersService;

    @Autowired
    public AuthenticationController(MessageHandler messageHandler, UserDetailsServiceImpl userDetailsService,
                                    UsersRspository usersRspository, AuthenticationManager authenticationManager, EmailServiceImpl emailServiceImpl) {
        this.messageHandler = messageHandler;
        this.userDetailsService = userDetailsService;
        this.usersRspository = usersRspository;
        this.authenticationManager = authenticationManager;
        this.emailServiceImpl = emailServiceImpl;
    }

    @PostMapping()
    public ResponseEntity<UserDto> auth(@Valid @RequestBody AuthenticactionAuthDto newUser) throws Exception {
        Optional<Users> optionalUser = usersRspository.findByEmail(newUser.getEmail());
        if (optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageHandler.userFound);
        }
        UserDto users = userDetailsService.save(newUser);
        emailServiceImpl.sendRegisterConfirmation(users.getEmail(), users.getFirstName());
        return ResponseEntity.status(HttpStatus.CREATED).body(users);
    }

    @PostMapping(LOGIN)
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequestDto request) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = (User) authenticate.getPrincipal();
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, JwtUtils.generateAccessToken(user))
                    .build();
        } catch (Exception e) {
            Map<String, Object> body = new HashMap<>();
            body.put("ok", Boolean.FALSE);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }
    }


    @GetMapping(MYINFO)
    public ResponseEntity<?> myInfo(){
        try {
           UserDto user = usersService.myInfo();
           return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        catch (Exception e) {
            Map<String, Object> body = new HashMap<>();
            body.put("ok", Boolean.FALSE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
    }

//    @GetMapping(MYINFO)
//    public ResponseEntity<String> getBaseUrl(@RequestHeader HttpServletRequest request) {
//        String token = request.getHeader("Authorization");
//
//        return ResponseEntity.status(HttpStatus.OK).body(token);
//    }
//    @GetMapping(MYINFO)
//    public ResponseEntity<String> getBaseUrl() {
//        String hola = "esto funciona";
//        return new ResponseEntity<String>(String.format(hola), HttpStatus.OK);
//    }
}