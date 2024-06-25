package com.fitfinance.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentSummary {
    private BigDecimal totalStocks;
    private BigDecimal totalFIIs;
    private BigDecimal totalFixedIncome;
}