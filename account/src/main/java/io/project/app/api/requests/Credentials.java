package io.project.app.api.requests;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author armena
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Credentials implements Serializable {

    private static final long serialVersionUID = -7482173754004269650L;

    private String id;
    private String email;
    private String phone;
    private String accountType;

}
