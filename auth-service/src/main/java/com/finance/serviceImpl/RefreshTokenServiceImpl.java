package com.finance.serviceImpl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.model.RefreshToken;
import com.finance.repository.RefreshTokenRepository;
import com.finance.service.RefreshTokenService;
import com.finance.utils.JwtUtils;

import jakarta.transaction.Transactional;

@Service
public class RefreshTokenServiceImpl  implements RefreshTokenService{
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	private static final long REFRESH_TOKEN_GENERATION = 1000*60*60*24*7;

	@Transactional
	@Override
	public RefreshToken createRefreshToken(String userEmail) {

		refreshTokenRepository.deleteByUserEmail(userEmail);
				
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setUserEmail(userEmail);
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setExpiryDate(Instant.now().plusMillis(REFRESH_TOKEN_GENERATION));
		return refreshTokenRepository.save(refreshToken);
	}

	@Override
    public RefreshToken verifyExpiration(String token) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }

    @Override
    public void deleteByUserEmail(String userEmail) {
        refreshTokenRepository.deleteByUserEmail(userEmail);
    }

}
