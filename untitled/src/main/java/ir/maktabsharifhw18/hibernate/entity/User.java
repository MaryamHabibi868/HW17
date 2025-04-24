package ir.maktabsharifhw18.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    @Id
    @SequenceGenerator(
            name = "users_seq_gen",
            allocationSize = 1,
            initialValue = 300
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "users_seq_gen")
    private Integer id;


    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    @OneToMany (mappedBy = "user")
    private Set<Card> cards;
}
