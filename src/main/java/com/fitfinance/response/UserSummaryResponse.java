package com.fitfinance.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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