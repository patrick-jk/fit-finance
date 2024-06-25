package com.fitfinance.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeSummary {
    private BigDecimal balance;
    private BigDecimal totalExpenses;
    private Finance biggestExpense;
    private Finance smallestExpense;
    private Investment biggestInvestment;
    private Investment smallestInvestment;
}
