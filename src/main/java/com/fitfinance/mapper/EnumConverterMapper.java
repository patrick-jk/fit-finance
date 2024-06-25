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
            case INCOME -> "Renda";
            case EXPENSE -> "Despesa";
        };
    }

    @InvestmentEnumConverterMapping
    public String convertInvestmentTypeToString(InvestmentType investmentType) {
        return switch (investmentType) {
            case STOCK -> "Ações";
            case FII -> "FIIs";
            case FIXED_INCOME -> "Renda Fixa";
        };
    }
}
