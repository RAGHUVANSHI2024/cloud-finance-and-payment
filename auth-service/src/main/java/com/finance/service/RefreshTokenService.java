package com.finance.service;

import com.finance.model.RefreshToken;

public interface RefreshTokenService {

	RefreshToken createRefreshToken(String userEmail);
	
	RefreshToken verifyExpiration(String token);
	
	void deleteByUserEmail(String email);
}
