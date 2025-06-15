package com.diemdt.literaturemuseum.controller;

import com.diemdt.literaturemuseum.entity.User;
import com.diemdt.literaturemuseum.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(@PageableDefault(size = 10) Pageable pageable, @RequestParam(required = false) String search){
        return ResponseEntity.ok(userService.getAllUsers(pageable, search));
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }
}
