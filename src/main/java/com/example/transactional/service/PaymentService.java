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
        Transaction savedObject = transactionService.save(payment.toBuilder().status("PROCESSING").build());
        try {
            HttpResponse<String> response = httpClient.send(HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/v1/mock?delay=true")).build());
            Transaction.TransactionBuilder builder = savedObject.toBuilder();
            if (response.statusCode()==200) {
                builder.status(response.body());
            } else {
                builder.status("ERROR");
            }
            return Optional.of(transactionService.save(builder.build()));
        } catch (HttpConnectTimeoutException e) {
            log.warn("Error in payment operation {}", e.getMessage());
        }
        return Optional.empty();
    }
}
