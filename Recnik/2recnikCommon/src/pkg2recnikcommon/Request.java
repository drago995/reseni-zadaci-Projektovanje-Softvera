/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg2recnikcommon;

import java.io.Serializable;

/**
 *
 * @author Ljubomir
 */
public class Request implements Serializable {
    Object arg;
    Operation op;

    public Request(Object arg, Operation op) {
        this.arg = arg;
        this.op = op;
    }

    public Object getArg() {
        return arg;
    }

    public void setArg(Object arg) {
        this.arg = arg;
    }

    public Operation getOp() {
        return op;
    }

    public void setOp(Operation op) {
        this.op = op;
    }
    
}
