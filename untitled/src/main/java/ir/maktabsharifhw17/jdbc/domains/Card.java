package ir.maktabsharifhw17.jdbc.domains;

import ir.maktabsharifhw17.jdbc.domains.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString (callSuper = true)
@NoArgsConstructor
public class Card extends BaseEntity<Integer> {

    public static final String TABLE_NAME = "cards";

    public static final String CARD_NUMBER = "card_number";
    public static final String BANK_NAME = "bank_name";
    public static final String BALANCE = "balance";
    public static final String EXPIRED_DATE = "expired_date";


    private String cardNumber;
    private BankName bankName;
    private Double balance;
    private LocalDate expiredDate;
    private User user;


    public Card(String cardNumber, BankName bankName,
                Double balance, LocalDate expiredDate, User user) {
        this.cardNumber = cardNumber;
        this.bankName = bankName;
        this.balance = balance;
        this.expiredDate = expiredDate;
        this.user = user;
    }
}
