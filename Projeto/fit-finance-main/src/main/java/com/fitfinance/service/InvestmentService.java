package com.fitfinance.service;

import com.fitfinance.domain.Investment;
import com.fitfinance.domain.InvestmentSummary;
import com.fitfinance.domain.InvestmentType;
import com.fitfinance.domain.User;
import com.fitfinance.exception.NotFoundException;
import com.fitfinance.repository.InvestmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvestmentService {
    private final InvestmentRepository investmentRepository;

    public List<Investment> getAllInvestments() {
        return investmentRepository.findAll();
    }

    public Investment findById(Long id) {
        return investmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Investment not found"));
    }

    public List<Investment> findByUserId(Long userId) {
        return investmentRepository.findByUserId(userId);
    }

    public Investment getBiggestInvestment(Long userId) {
        return investmentRepository.getBiggestInvestmentByUserId(userId).orElseThrow(() -> new NotFoundException("Biggest investment not found"));
    }

    public Investment getSmallestInvestment(Long userId) {
        return investmentRepository.getSmallestInvestmentByUserId(userId).orElseThrow(() -> new NotFoundException("Smallest investment not found"));
    }

    public InvestmentSummary getInvestmentSummary(Long userId) {
        var totalStocks = investmentRepository.getTotalInvestmentByType(userId, InvestmentType.STOCK);
        var totalFIIs = investmentRepository.getTotalInvestmentByType(userId, InvestmentType.FII);
        var totalFixedIncome = investmentRepository.getTotalInvestmentByType(userId, InvestmentType.FIXED_INCOME);

        return InvestmentSummary.builder()
                .totalStocks(totalStocks)
                .totalFIIs(totalFIIs)
                .totalFixedIncome(totalFixedIncome)
                .build();
    }

    @Transactional
    public Investment createInvestment(Investment investment) {
        return investmentRepository.save(investment);
    }

    public void updateInvestment(Investment investmentToUpdate) {
        Investment foundInvestment = findById(investmentToUpdate.getId());
        foundInvestment.setUser(investmentToUpdate.getUser());

        if (!investmentToUpdate.getUser().getId().equals(foundInvestment.getUser().getId())) {
            throw new SecurityException("User does not have permission");
        }

        investmentRepository.save(investmentToUpdate);
    }

    public void deleteInvestment(Long id, User user) {
        var investment = findById(id);

        if (!investment.getUser().getId().equals(user.getId())) {
            throw new SecurityException("User does not have permission");
        }

        investmentRepository.delete(investment);
    }
}
