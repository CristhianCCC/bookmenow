package com.businessdomain.user.controller;
import com.businessdomain.user.dto.UserDTO;
import com.businessdomain.user.model.User;
import com.businessdomain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List> getAllUsers () {
        List userfound = userService.getAllUsers();
        return ResponseEntity.ok().body(userfound);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById (@PathVariable Long id){
        UserDTO userFound = userService.getUserById(id);
        return ResponseEntity.ok(userFound);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser (@RequestBody UserDTO userDTO){
        UserDTO userCreated = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> editUser (@PathVariable Long id, @RequestBody UserDTO userDTO){
        UserDTO userEdited = userService.editUser(id, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userEdited);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser (@PathVariable Long id){
         userService.deleteUser(id);
         return ResponseEntity.noContent().build();
    }
}
