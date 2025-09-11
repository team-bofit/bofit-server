package org.sopt.bofit.domain.user.repository;

import java.util.Optional;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.constant.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByOauthId(String oauthId);
    Optional<User> findByIdAndStatus(Long id, UserStatus userStatus);
}
