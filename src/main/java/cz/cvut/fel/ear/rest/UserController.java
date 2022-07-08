/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.ear.rest;

import cz.cvut.fel.ear.exception.NotFoundException;
import cz.cvut.fel.ear.model.User;
import cz.cvut.fel.ear.rest.util.RestUtils;
import cz.cvut.fel.ear.security.model.AuthenticationToken;
import cz.cvut.fel.ear.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/user")
public class UserController {
    
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getById(@PathVariable Integer id) {
        final User user = userService.getById(id);
        if (user == null) {
            throw NotFoundException.create("User", id);
        }
        return user;
    }
    
    
    
//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> registerUser(@RequestBody User user) {
//        userService.persist(user);
//        LOG.debug("User {} successfully registered.", user);
//        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/current");
//        return new ResponseEntity<>(headers, HttpStatus.CREATED);
//    }
//    
    
//    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
//    public User getCurrent(Principal principal) {
//        final AuthenticationToken auth = (AuthenticationToken) principal;
//        return auth.getPrincipal().getUser();
//    }
}
