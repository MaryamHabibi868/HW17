package ir.maktabsharifhw18.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Transaction {

    @Id
    @SequenceGenerator(
            name = "transaction_seq_gen",
            allocationSize = 1,
            initialValue = 200
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "transaction_seq_gen")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "source_card_id")
    private Card sourceCard;

    @ManyToOne
    @JoinColumn(name = "destination_card_id")
    private Card destinationCard;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    private LocalDateTime transactionDate;


}
