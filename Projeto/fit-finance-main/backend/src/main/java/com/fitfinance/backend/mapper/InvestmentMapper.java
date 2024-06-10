package com.fitfinance.backend.mapper;

import com.fitfinance.backend.domain.Investment;
import com.fitfinance.backend.request.InvestmentPostRequest;
import com.fitfinance.backend.request.InvestmentPutRequest;
import com.fitfinance.backend.response.InvestmentGetResponse;
import com.fitfinance.backend.response.InvestmentPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvestmentMapper {
    Investment toInvestment(InvestmentPostRequest request);

    @Mapping(source = "id", target = "id")
    Investment toInvestment(InvestmentPutRequest request);

    InvestmentPostResponse toInvestmentPostResponse(Investment investment);

    InvestmentGetResponse toInvestmentGetResponse(Investment investment);

    List<InvestmentGetResponse> toInvestmentGetResponses(List<Investment> investment);
}