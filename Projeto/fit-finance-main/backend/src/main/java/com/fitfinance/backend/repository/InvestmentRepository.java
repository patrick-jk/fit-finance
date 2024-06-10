package com.fitfinance.backend.repository;

import com.fitfinance.backend.domain.Investment;
import com.fitfinance.backend.domain.InvestmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByNameContaining(String name);
    List<Investment> findByType(InvestmentType type);
    List<Investment> findByUserId(Long id);
}
