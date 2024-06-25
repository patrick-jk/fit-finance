package com.fitfinance.mapper;

import com.fitfinance.domain.HomeSummary;
import com.fitfinance.domain.InvestmentSummary;
import com.fitfinance.response.HomeSummaryResponse;
import com.fitfinance.response.InvestmentGetResponse;
import com.fitfinance.response.InvestmentSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvestmentSummaryMapper {
    InvestmentSummaryResponse toInvestmentSummaryResponse(InvestmentSummary investmentSummary);
}