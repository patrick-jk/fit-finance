package com.fitfinance.repository;

import com.fitfinance.domain.Investment;
import com.fitfinance.domain.InvestmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    List<Investment> findByNameContaining(String name);

    List<Investment> findByType(InvestmentType type);

    List<Investment> findByUserId(Long id);

    @Query("SELECT i FROM Investment i WHERE i.user.id = :userId ORDER BY i.price * i.quantity DESC LIMIT 1")
    Optional<Investment> getBiggestInvestmentByUserId(Long userId);

    @Query("SELECT i FROM Investment i WHERE i.user.id = :userId ORDER BY i.price * i.quantity ASC LIMIT 1")
    Optional<Investment> getSmallestInvestmentByUserId(Long userId);

    @Query("SELECT COALESCE(SUM(i.price * i.quantity), 0) FROM Investment i WHERE i.user.id = :userId AND i.type = :type")
    BigDecimal getTotalInvestmentByType(Long userId, InvestmentType type);
}
