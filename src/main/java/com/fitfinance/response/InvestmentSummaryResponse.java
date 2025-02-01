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
public class InvestmentSummaryResponse {

    private BigDecimal totalStocks;
    private BigDecimal totalFIIs;
    private BigDecimal totalFixedIncome;
}