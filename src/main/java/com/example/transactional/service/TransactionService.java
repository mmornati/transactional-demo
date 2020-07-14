package com.example.transactional.service;

import com.example.transactional.entity.Transaction;
import com.example.transactional.repository.TransactionRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    final TransactionRepository transactionRepository;

    public List<com.example.transactional.dto.Transaction> findAll() {
        return StreamSupport.stream(transactionRepository.findAll().spliterator(), false)
                .map(Transaction::project).collect(Collectors.toList());
    }

    public Optional<com.example.transactional.dto.Transaction> findById(Long id) {
        return transactionRepository.findById(id).map(Transaction::project);
    }

    @Transactional
    public com.example.transactional.dto.Transaction saveWithConversion(com.example.transactional.dto.Transaction transactionDto) {
        return transactionRepository.save(
                Transaction.builder()
                        .amount(transactionDto.getAmount())
                        .orderId(transactionDto.getOrderId())
                        .status(transactionDto.getStatus())
                        .build()
        ).project();
    }

    @Transactional
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
