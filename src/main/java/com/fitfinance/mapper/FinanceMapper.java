package com.fitfinance.mapper;

import com.fitfinance.annotation.FinanceEnumConverterMapping;
import com.fitfinance.domain.Finance;
import com.fitfinance.request.FinancePostRequest;
import com.fitfinance.request.FinancePutRequest;
import com.fitfinance.response.FinanceGetResponse;
import com.fitfinance.response.FinancePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = EnumConverterMapper.class)
public interface FinanceMapper {

    @Mapping(source = "type", target = "type")
    Finance toFinance(FinancePostRequest request);

    @Mapping(source = "id", target = "id")
    Finance toFinance(FinancePutRequest request);

    @Mapping(target = "type", qualifiedBy = FinanceEnumConverterMapping.class)
    FinancePostResponse toFinancePostResponse(Finance finance);

    @Mapping(target = "type", qualifiedBy = FinanceEnumConverterMapping.class)
    FinanceGetResponse toFinanceGetResponse(Finance finance);

    List<FinanceGetResponse> toFinanceGetResponses(List<Finance> finances);
}