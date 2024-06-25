package com.fitfinance.mapper;

import com.fitfinance.response.model.HomeSummary;
import com.fitfinance.response.HomeSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HomeSummaryMapper {
    @Mapping(source = "totalExpenses", target = "totalExpenses")
    HomeSummaryResponse toHomeSummaryResponse(HomeSummary homeSummary);
}