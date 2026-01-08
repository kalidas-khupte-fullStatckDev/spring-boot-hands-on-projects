package com.social.media.kgram.controller;

import com.social.media.kgram.models.PlatformUser;
import com.social.media.kgram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("get/all")
    public ResponseEntity<List<PlatformUser>> getAllUsers(){
        List<PlatformUser> platformUserList = userService.getAllUsers();
        return new ResponseEntity<>(platformUserList, HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @PostMapping("add/new")
    public ResponseEntity<PlatformUser> addNewUser(@RequestBody PlatformUser platformUser){
        PlatformUser newUser = userService.addNewUser(platformUser);
        return new ResponseEntity<>(newUser, HttpStatusCode.valueOf(HttpStatus.CREATED.value()));
    }
}
