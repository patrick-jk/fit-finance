package com.fitfinance.backend.util;

import com.fitfinance.backend.domain.Token;
import com.fitfinance.backend.domain.TokenType;
import com.fitfinance.backend.domain.User;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {
    public Token createValidToken(String jwtToken, User user) {
        return Token.builder()
                .tokenType(TokenType.BEARER)
                .expired(false)
                .tokenString(jwtToken)
                .user(user)
                .build();
    }
}
