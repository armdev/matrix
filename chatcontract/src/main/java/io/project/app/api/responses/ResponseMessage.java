/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.api.responses;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author root
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage implements Serializable {

    private String message;

}
