package io.project.app.api.responses;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import io.project.app.domain.Account;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author armen
 */
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ApiAccountResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Account account = new Account();

    private String token;
    private List<Account> accountList = new ArrayList<>();

}
