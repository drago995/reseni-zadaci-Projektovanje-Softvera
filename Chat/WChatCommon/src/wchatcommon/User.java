/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wchatcommon;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author Ljubomir
 */
public class User implements Serializable{
    String name;
    String surname;
    String email;
    String password;

    public User() {
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "name=" + name + ", surname=" + surname + ", email=" + email + ", password=" + password + '}';
    }
    
    
    
    
}
