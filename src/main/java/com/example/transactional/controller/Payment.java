package com.example.transactional.controller;

import com.example.transactional.dto.Transaction;
import com.example.transactional.service.PaymentService;
import com.example.transactional.service.TransactionService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class Payment {

    final PaymentService paymentService;
    final TransactionService transactionService;

    @PostMapping
    @ResponseBody
    public Optional<Transaction> actionOnPayment(@RequestBody final Transaction request) {
        return paymentService.payment(request);
    }

    @GetMapping
    @ResponseBody
    public List<Transaction> actionOnPayment() {
        return transactionService.findAll();
    }
}
