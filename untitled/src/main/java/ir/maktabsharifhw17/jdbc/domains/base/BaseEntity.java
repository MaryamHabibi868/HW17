package ir.maktabsharifhw17.jdbc.domains.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public abstract class BaseEntity <ID> {

    public static final String ID_COLUMN = "id";

    private ID id;
}
