package com.fitfinance.mapper;

import com.fitfinance.response.model.InvestmentSummary;
import com.fitfinance.response.InvestmentSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvestmentSummaryMapper {
    InvestmentSummaryResponse toInvestmentSummaryResponse(InvestmentSummary investmentSummary);
}