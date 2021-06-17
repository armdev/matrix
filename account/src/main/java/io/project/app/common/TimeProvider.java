package io.project.app.common;

import java.io.Serializable;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class TimeProvider implements Serializable {

    private static final long serialVersionUID = -3301695478208950415L;

    public Date now() {
        return new Date();
    }
}