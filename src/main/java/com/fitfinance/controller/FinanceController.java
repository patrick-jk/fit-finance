package com.fitfinance.controller;

import com.fitfinance.domain.Finance;
import com.fitfinance.domain.User;
import com.fitfinance.mapper.FinanceMapper;
import com.fitfinance.request.FinancePostRequest;
import com.fitfinance.request.FinancePutRequest;
import com.fitfinance.response.FinanceGetResponse;
import com.fitfinance.response.FinancePostResponse;
import com.fitfinance.response.UserSummaryResponse;
import com.fitfinance.service.FinanceService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("api/v1/finances")
@RequiredArgsConstructor
public class FinanceController {

  private final FinanceService financeService;
  private final FinanceMapper mapper;

  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<List<FinanceGetResponse>> findAll() {
    List<Finance> allFinances = financeService.getAllFinances();
    var financeGetResponses = mapper.toFinanceGetResponses(allFinances);
    return ResponseEntity.ok(financeGetResponses);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Finance> findById(@PathVariable Long id) {
    var financeFound = financeService.findById(id);
    return ResponseEntity.ok(financeFound);
  }

  @GetMapping("/by-user-id")
  public ResponseEntity<List<FinanceGetResponse>> findByUserId() {
    var user = getUser();
    var financeFound = financeService.findByUserId(user.getId());

    var financesResponse = mapper.toFinanceGetResponses(financeFound);
    return ResponseEntity.ok(financesResponse);
  }

  @GetMapping("/biggest-expense")
  public ResponseEntity<FinanceGetResponse> getBiggestExpense() {
    var user = getUser();
    var financeFound = financeService.getBiggestExpense(user.getId());
    var financeGetResponse = mapper.toFinanceGetResponse(financeFound);
    return ResponseEntity.ok(financeGetResponse);
  }

  @GetMapping("/smallest-expense")
  public ResponseEntity<FinanceGetResponse> getSmallestExpense() {
    var user = getUser();
    var financeFound = financeService.getSmallestExpense(user.getId());
    var financeGetResponse = mapper.toFinanceGetResponse(financeFound);
    return ResponseEntity.ok(financeGetResponse);
  }

  @GetMapping("/user-summary")
  public ResponseEntity<UserSummaryResponse> getFinanceBalance() {
    var user = getUser();
    var userSummaryResponse = financeService.getUserSummary(user.getId());

    return ResponseEntity.ok(userSummaryResponse);
  }

  @PostMapping
  public ResponseEntity<FinancePostResponse> create(@RequestBody @Valid FinancePostRequest financePostRequest) {
    var user = getUser();
    var finance = mapper.toFinance(financePostRequest);
    finance.setUser(user);
    Finance savedFinance = financeService.createFinance(finance);
    var response = mapper.toFinancePostResponse(savedFinance);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping
  public ResponseEntity<Void> updateFinance(@RequestBody @Valid FinancePutRequest financePutRequest) {
    var user = getUser();
    var financeToUpdate = mapper.toFinance(financePutRequest);
    financeToUpdate.setUser(user);
    financeService.updateFinance(financeToUpdate);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteFinance(@PathVariable Long id) {
    financeService.deleteFinance(id, getUser());
    return ResponseEntity.noContent().build();
  }

  private static User getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (User) authentication.getPrincipal();
  }
}
