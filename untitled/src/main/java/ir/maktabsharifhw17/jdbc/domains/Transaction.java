package ir.maktabsharifhw17.jdbc.domains;

import ir.maktabsharifhw17.jdbc.domains.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString ( callSuper = true)
@NoArgsConstructor
public class Transaction extends BaseEntity<Integer> {

    public static final String TABLE_NAME = "transactions";

    public static final String SOURCE_CARD_NUMBER = "source_card_number";
    public static final String DESTINATION_CARD_NUMBER = "destination_card_number";
    public static final String AMOUNT = "amount";
    public static final String TRANSACTION_TYPE = "transaction_type";
    public static final String TRANSACTION_STATUS = "transaction_status";
    public static final String TRANSACTION_DATE = "transaction_date";

    private Card sourceCardNumber;
    private Card destinationCardNumber;
    private Double amount;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private LocalDateTime transactionDate;


}
