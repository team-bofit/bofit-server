package org.sopt.bofit.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
}
