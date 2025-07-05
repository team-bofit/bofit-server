package org.sopt.bofit.domain.user.repository;

import org.sopt.bofit.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByOauthId(String oauthId);
}
