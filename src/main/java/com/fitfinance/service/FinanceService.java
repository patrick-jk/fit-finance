package com.fitfinance.service;

import com.fitfinance.domain.Finance;
import com.fitfinance.response.model.HomeSummary;
import com.fitfinance.domain.User;
import com.fitfinance.exception.NotFoundException;
import com.fitfinance.repository.FinanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class FinanceService {
    private final FinanceRepository financeRepository;
    private final InvestmentService investmentService;

    public List<Finance> getAllFinances() {
        return financeRepository.findAll();
    }

    public Finance findById(Long id) {
        return financeRepository.findById(id).orElseThrow(() -> new NotFoundException("Finance not found"));
    }

    public List<Finance> findByUserId(Long userId) {
        return financeRepository.findByUserId(userId);
    }

    public Finance getBiggestExpense(Long userId) {
        return financeRepository.getBiggestExpenseByUserId(userId).orElseThrow(() -> new NotFoundException("Biggest expense not found"));
    }

    public Finance getSmallestExpense(Long userId) {
        return financeRepository.getSmallestExpenseByUserId(userId).orElseThrow(() -> new NotFoundException("Smallest expense not found"));
    }

    private BigDecimal getTotalExpenses(Long userId) {
        return financeRepository.getExpensesTotalByUserId(userId);
    }

    public HomeSummary getFinanceBalance(Long userId) {
        var balance = financeRepository.findBalanceByUserId(userId);
        var totalExpenses = getTotalExpenses(userId);
        var biggestExpense = getBiggestExpense(userId);
        var smallestExpense = getSmallestExpense(userId);
        var biggestInvestment = investmentService.getBiggestInvestment(userId);
        var smallestInvestment = investmentService.getSmallestInvestment(userId);

        return HomeSummary.builder()
                .balance(balance)
                .totalExpenses(totalExpenses)
                .biggestExpense(biggestExpense)
                .smallestExpense(smallestExpense)
                .biggestInvestment(biggestInvestment)
                .smallestInvestment(smallestInvestment)
                .build();
    }

    @Transactional
    public Finance createFinance(Finance finance) {
        return financeRepository.save(finance);
    }

    public void updateFinance(Finance financeToUpdate) {
        Finance foundFinance = findById(financeToUpdate.getId());
        foundFinance.setUser(financeToUpdate.getUser());

        if (!financeToUpdate.getUser().getId().equals(foundFinance.getUser().getId())) {
            throw new SecurityException("User does not have permission");
        }

        financeRepository.save(financeToUpdate);
    }

    public void deleteFinance(Long id, User user) {
        var finance = findById(id);

        if (!finance.getUser().getId().equals(user.getId())) {
            throw new SecurityException("User does not have permission");
        }

        financeRepository.delete(finance);
    }
}
