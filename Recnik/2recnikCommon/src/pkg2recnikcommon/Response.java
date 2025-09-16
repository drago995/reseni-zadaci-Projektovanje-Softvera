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
public class Response implements Serializable {
    Object result;
    Exception ex;

    public Response() {
    }

    public Response(Object result, Exception ex) {
        this.result = result;
        this.ex = ex;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }
    
    
}
