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
public class InvestmentSummaryResponse {

  private BigDecimal totalStocks;
  private BigDecimal totalFIIs;
  private BigDecimal totalFixedIncome;
}