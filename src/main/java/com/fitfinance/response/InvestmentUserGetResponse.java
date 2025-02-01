package com.fitfinance.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InvestmentUserGetResponse {

    @Schema(description = "User's id", example = "0001")
    private Long id;
    @Schema(description = "User's income", example = "1500.00")
    private double income;
}