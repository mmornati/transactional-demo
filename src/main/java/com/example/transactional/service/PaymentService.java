package com.example.transactional.service;

import com.example.transactional.dto.Transaction;
import com.example.transactional.utils.HttpClient;
import java.net.URI;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    final TransactionService transactionService;
    final HttpClient httpClient;

    @Transactional
    public Optional<Transaction> payment(Transaction payment) {
        com.example.transactional.entity.Transaction savedObject = transactionService.save(
                com.example.transactional.entity.Transaction.builder()
                                .amount(payment.getAmount())
                                .orderId(payment.getOrderId())
                                .status("PROCESSING")
                        .build()
        );
        try {
            HttpResponse<String> response = httpClient.send(HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/v1/mock?delay=true")).build());
            if (response.statusCode()==200) {
                savedObject.setStatus(response.body());
            } else {
                savedObject.setStatus("ERROR");
            }
            return Optional.of(savedObject.project());
        } catch (HttpConnectTimeoutException e) {
            log.warn("Error in payment operation {}", e.getMessage());
        }
        return Optional.empty();
    }
}
