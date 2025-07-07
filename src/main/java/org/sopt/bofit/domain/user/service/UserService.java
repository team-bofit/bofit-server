package org.sopt.bofit.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

}
