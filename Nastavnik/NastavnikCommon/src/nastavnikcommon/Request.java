/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nastavnikcommon;

import java.io.Serializable;

/**
 *
 * @author Ljubomir
 */
public class Request implements Serializable {
    private Operation op;
    private Object argument;

    public Request(Operation op, Object argument) {
        this.op = op;
        this.argument = argument;
    }

    public Request() {
    }
    

    public Operation getOp() {
        return op;
    }

    public void setOp(Operation op) {
        this.op = op;
    }

    public Object getArgument() {
        return argument;
    }

    public void setArgument(Object argument) {
        this.argument = argument;
    }
    
}
