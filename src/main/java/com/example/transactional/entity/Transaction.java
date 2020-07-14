package com.example.transactional.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "TRANSACTION")
@SequenceGenerator(name = "transaction_id_generator", sequenceName = "SEQ_TRANSACTION",
        allocationSize = 1)
@EqualsAndHashCode(of = {"id", "orderId"})
@ToString(of = {"id", "orderId"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
public class Transaction implements Serializable {

    @Id
    @Column(name = "TRANSACT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_generator")
    private Long id;

    @Column(name = "ORDER_ID")
    private String orderId;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "STATUS")
    private String status;

    public com.example.transactional.dto.Transaction project() {
        return com.example.transactional.dto.Transaction.builder()
                .id(id)
                .amount(amount)
                .orderId(orderId)
                .status(status)
                .build();
    }
}
