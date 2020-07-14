package com.example.transactional.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder(toBuilder = true)
public class Transaction {
    Long id;
    String orderId;
    Double amount;
    String status;
}
