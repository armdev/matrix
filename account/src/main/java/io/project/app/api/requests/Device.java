package io.project.app.api.requests;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Device implements Serializable{

    private static final long serialVersionUID = -2648039108776114791L;

    private boolean normal;
    private boolean tablet;
    private boolean mobile;

}
