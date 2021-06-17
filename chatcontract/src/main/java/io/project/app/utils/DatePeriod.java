/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.utils;

import java.util.Date;

/**
 *
 * @author armena
 */
public class DatePeriod {

    private Date start;
    private Date end;

    public DatePeriod() {
        this.start = null;
        this.end = null;
    }

    public DatePeriod(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
