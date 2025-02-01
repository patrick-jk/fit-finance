package com.fitfinance.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
public class UserSummaryResponse {

    private BigDecimal balance;
    private BigDecimal totalExpenses;
    private FinanceGetResponse biggestExpense;
    private FinanceGetResponse smallestExpense;
    private InvestmentGetResponse biggestInvestment;
    private InvestmentGetResponse smallestInvestment;
}