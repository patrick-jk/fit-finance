package com.fitfinance.backend.mapper;

import com.fitfinance.backend.domain.Finance;
import com.fitfinance.backend.request.FinancePostRequest;
import com.fitfinance.backend.request.FinancePutRequest;
import com.fitfinance.backend.response.FinanceGetResponse;
import com.fitfinance.backend.response.FinancePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FinanceMapper {
    @Mapping(source = "type", target = "type")
    Finance toFinance(FinancePostRequest request);

    @Mapping(source = "id", target = "id")
    Finance toFinance(FinancePutRequest request);

    FinancePostResponse toFinancePostResponse(Finance finance);

    FinanceGetResponse toFinanceGetResponse(Finance finance);

    List<FinanceGetResponse> toFinanceGetResponses(List<Finance> finances);
}