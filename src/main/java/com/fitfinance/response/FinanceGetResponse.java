package com.fitfinance.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FinanceGetResponse {

  @Schema(description = "Finance's ID", example = "1")
  private Long id;
  @Schema(description = "Finance's name", example = "Salary")
  private String name;
  @Schema(description = "Finance's description", example = "Monthly salary")
  private String description;
  @Schema(description = "Finance's value", example = "1000.00")
  private double value;
  @Schema(description = "Finance's type", example = "INCOME")
  private String type;
  @Schema(description = "Finance's start date", example = "2021-01-01")
  @JsonProperty("start_date")
  private LocalDate startDate;
  @Schema(description = "Finance's end date", example = "2021-01-31")
  @JsonProperty("end_date")
  private LocalDate endDate;
  @Schema(description = "Finance's user", example = "User")
  private FinanceUserGetResponse user;
}