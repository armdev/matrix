package io.project.app.api.requests;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author armen
 */
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class RegisterRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    
    private String email;

    private String password;

}
