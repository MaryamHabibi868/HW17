package ir.maktabsharifhw17.jdbc.domains;

import ir.maktabsharifhw17.jdbc.domains.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString ( callSuper = true )
@NoArgsConstructor
public class User extends BaseEntity<Integer> {

    public static final String TABLE_NAME = "users";

    public static final String FIRST_NAME_COLUMN = "first_name";
    public static final String LAST_NAME_COLUMN = "last_name";
    public static final String USER_NAME_COLUMN = "user_name";
    public static final String PASSWORD_COLUMN = "password";

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
}
