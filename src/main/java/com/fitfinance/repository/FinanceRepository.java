package com.fitfinance.repository;

import com.fitfinance.domain.Finance;
import com.fitfinance.domain.FinanceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface FinanceRepository extends JpaRepository<Finance, Long> {

    List<Finance> findByNameContaining(String name);

    List<Finance> findByType(FinanceType type);

    List<Finance> findByUserId(Long id);

    @Query("SELECT f FROM Finance f WHERE f.type = 'EXPENSE' AND f.user.id = :userId ORDER BY f.value DESC LIMIT 1")
    Optional<Finance> getBiggestExpenseByUserId(Long userId);

    @Query("SELECT f FROM Finance f WHERE f.type = 'EXPENSE' AND f.user.id = :userId ORDER BY f.value ASC LIMIT 1")
    Optional<Finance> getSmallestExpenseByUserId(Long userId);

    @Query(
            "SELECT f.user.income + SUM(CASE WHEN f.type = 'INCOME' THEN f.value ELSE 0 END) - SUM(CASE WHEN f.type = 'EXPENSE' THEN f.value ELSE 0 END) "
                    +
                    "FROM Finance f WHERE f.user.id = :userId")
    BigDecimal findBalanceByUserId(Long userId);

    @Query("SELECT SUM(CASE WHEN f.type = 'EXPENSE' THEN f.value ELSE 0 END) FROM Finance f WHERE f.user.id = :userId")
    BigDecimal getTotalExpensesByUserId(Long userId);
}