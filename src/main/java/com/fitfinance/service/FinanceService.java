package com.fitfinance.service;

import com.fitfinance.domain.Finance;
import com.fitfinance.domain.User;
import com.fitfinance.exception.NotFoundException;
import com.fitfinance.mapper.FinanceMapper;
import com.fitfinance.mapper.InvestmentMapper;
import com.fitfinance.repository.FinanceRepository;
import com.fitfinance.response.UserSummaryResponse;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class FinanceService {

  private final FinanceRepository financeRepository;
  private final InvestmentService investmentService;
  private final FinanceMapper financeMapper;
  private final InvestmentMapper investmentMapper;

  private static final Finance NOT_FOUND_FINANCE = new Finance(null, null, 0.0, null, null, null, null, null);

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
    return financeRepository.getBiggestExpenseByUserId(userId).orElse(NOT_FOUND_FINANCE);
  }

  public Finance getSmallestExpense(Long userId) {
    return financeRepository.getSmallestExpenseByUserId(userId).orElse(NOT_FOUND_FINANCE);
  }

  private BigDecimal getTotalExpenses(Long userId) {
    return financeRepository.getTotalExpensesByUserId(userId);
  }

  public UserSummaryResponse getUserSummary(Long userId) {
    var balance = financeRepository.findBalanceByUserId(userId);
    var totalExpenses = getTotalExpenses(userId);
    var biggestExpense = financeMapper.toFinanceGetResponse(getBiggestExpense(userId));
    var smallestExpense = financeMapper.toFinanceGetResponse(getSmallestExpense(userId));
    var biggestInvestment = investmentMapper.toInvestmentGetResponse(investmentService.getBiggestInvestment(userId));
    var smallestInvestment = investmentMapper.toInvestmentGetResponse(investmentService.getSmallestInvestment(userId));

    return UserSummaryResponse.builder()
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
