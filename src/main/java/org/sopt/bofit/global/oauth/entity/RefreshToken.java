package org.sopt.bofit.global.oauth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RefreshToken {
    @Id
    private Long userId;

    @Column(nullable = false, length = 500)
    private String refreshToken;

    public static RefreshToken of(Long userId, String refreshToken) {
        return new RefreshToken(userId, refreshToken);
    }

    public void updateToken(String token) {
        this.refreshToken = token;
    }
}
