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

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    final TransactionService transactionService;
    final HttpClient httpClient;

    public Optional<Transaction> payment(Transaction payment) {
        payment.setStatus("PROCESSING");
        Transaction savedObject = transactionService.save(payment);
        try {
            HttpResponse<String> response = httpClient.send(HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/v1/mock?delay=true")).build());
            if (response.statusCode()==200) {
                savedObject.setStatus(response.body());
            } else {
                savedObject.setStatus("ERROR");
            }
            return Optional.of(savedObject);
        } catch (HttpConnectTimeoutException e) {
            log.warn("Error in payment operation {}", e.getMessage());
        }
        return Optional.empty();
    }
}