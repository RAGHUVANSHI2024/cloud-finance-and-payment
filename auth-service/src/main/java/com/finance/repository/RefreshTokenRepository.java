package com.finance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finance.model.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>  {
	
	Optional<RefreshToken> findByToken(String token);
	
	void deleteByUserEmail(String email);

}
