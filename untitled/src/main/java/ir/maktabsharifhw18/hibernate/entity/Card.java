package ir.maktabsharifhw18.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Card {

    @Id
    @SequenceGenerator(
            name = "card_seq_gen",
            allocationSize = 1,
            initialValue = 100
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "card_seq_gen")
    private Integer id;


    private String cardNumber;

    @Enumerated(EnumType.STRING)
    private BankName bankName;

    private Double balance;

    private LocalDate expiredDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "sourceCard")
    private Set<Transaction> sentTransactions;

    @OneToMany(mappedBy = "destinationCard")
    private Set<Transaction> receivedTransactions;
}
