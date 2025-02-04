package com.fitfinance.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fitfinance.domain.InvestmentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class InvestmentPostRequest {

  @NotBlank(message = "The field 'name' is required")
  @Schema(description = "Investment's name", example = "Cachaça")
  private String name;
  @NotBlank(message = "The field 'value' is required")
  @Schema(description = "Investment's value", example = "150.00")
  private Double price;
  @NotBlank(message = "The field 'type' is required")
  @Schema(description = "Investment's type", example = "STOCK")
  private InvestmentType type;
  @NotBlank(message = "The field 'quantity' is required")
  @Schema(description = "Investment's quantity", example = "1")
  private Integer quantity;
  @NotBlank(message = "The field 'startDate' is required")
  @JsonProperty("start_date")
  private LocalDate startDate;
  @JsonProperty("end_date")
  private LocalDate endDate;
  @Schema(description = "Investment's user", example = "User")
  @JsonIgnore
  private UserPostRequest user;
}