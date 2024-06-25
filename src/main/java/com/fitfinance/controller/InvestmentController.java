package com.fitfinance.controller;

import com.fitfinance.domain.Investment;
import com.fitfinance.domain.User;
import com.fitfinance.mapper.InvestmentMapper;
import com.fitfinance.mapper.InvestmentSummaryMapper;
import com.fitfinance.request.InvestmentPostRequest;
import com.fitfinance.request.InvestmentPutRequest;
import com.fitfinance.response.InvestmentGetResponse;
import com.fitfinance.response.InvestmentPostResponse;
import com.fitfinance.response.InvestmentSummaryResponse;
import com.fitfinance.service.InvestmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("api/v1/investments")
@RequiredArgsConstructor
public class InvestmentController {
    private final InvestmentService investmentService;
    private final InvestmentMapper mapper;
    private final InvestmentSummaryMapper investmentSummaryMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<InvestmentGetResponse>> findAll() {
        List<Investment> allInvestments = investmentService.getAllInvestments();
        var investmentResponse = mapper.toInvestmentGetResponses(allInvestments);
        return ResponseEntity.ok(investmentResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<InvestmentGetResponse> findById(@PathVariable Long id) {
        var investmentFound = investmentService.findById(id);
        var investmentResponse = mapper.toInvestmentGetResponse(investmentFound);
        return ResponseEntity.ok(investmentResponse);
    }

    @GetMapping("/by-user-id")
    public ResponseEntity<List<InvestmentGetResponse>> findByUserId() {
        var user = getUser();
        var investmentFound = investmentService.findByUserId(user.getId());

        var investmentsResponse = mapper.toInvestmentGetResponses(investmentFound);
        return ResponseEntity.ok(investmentsResponse);
    }

    @GetMapping("/biggest-investment")
    public ResponseEntity<InvestmentGetResponse> getBiggestInvestment() {
        var user = getUser();
        var investmentFound = investmentService.getBiggestInvestment(user.getId());
        var investmentResponse = mapper.toInvestmentGetResponse(investmentFound);
        return ResponseEntity.ok(investmentResponse);
    }

    @GetMapping("/smallest-investment")
    public ResponseEntity<InvestmentGetResponse> getSmallestInvestment() {
        var user = getUser();
        var investmentFound = investmentService.getSmallestInvestment(user.getId());
        var investmentResponse = mapper.toInvestmentGetResponse(investmentFound);
        return ResponseEntity.ok(investmentResponse);
    }

    @GetMapping("/total-summary")
    public ResponseEntity<InvestmentSummaryResponse> getInvestmentSummary() {
        var user = getUser();
        var investmentSummary = investmentService.getInvestmentSummary(user.getId());
        var investmentSummaryResponse = investmentSummaryMapper.toInvestmentSummaryResponse(investmentSummary);
        return ResponseEntity.ok(investmentSummaryResponse);
    }

    @PostMapping
    public ResponseEntity<InvestmentPostResponse> create(@RequestBody @Valid InvestmentPostRequest investmentPostRequest) {
        var user = getUser();
        var investment = mapper.toInvestment(investmentPostRequest);
        investment.setUser(user);
        Investment savedInvestment = investmentService.createInvestment(investment);
        var response = mapper.toInvestmentPostResponse(savedInvestment);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<Void> updateInvestment(@RequestBody @Valid InvestmentPutRequest investmentPutRequest) {
        var user = getUser();
        var investmentToUpdate = mapper.toInvestment(investmentPutRequest);
        investmentToUpdate.setUser(user);
        investmentService.updateInvestment(investmentToUpdate);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvestment(@PathVariable Long id) {
        investmentService.deleteInvestment(id, getUser());
        return ResponseEntity.noContent().build();
    }

    private static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
