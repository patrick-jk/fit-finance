package com.fitfinance.mapper;

import com.fitfinance.annotation.FinanceEnumConverterMapping;
import com.fitfinance.annotation.InvestmentEnumConverterMapping;
import com.fitfinance.domain.FinanceType;
import com.fitfinance.domain.InvestmentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnumConverterMapper {

    @FinanceEnumConverterMapping
    public String convertFinanceTypeToString(FinanceType financeType) {
        return switch (financeType) {
            case INCOME -> "INCOME";
            case EXPENSE -> "EXPENSE";
        };
    }

    @InvestmentEnumConverterMapping
    public String convertInvestmentTypeToString(InvestmentType investmentType) {
        return switch (investmentType) {
            case STOCK -> "STOCK";
            case FII -> "FII";
            case FIXED_INCOME -> "FIXED_INCOME";
        };
    }
}
