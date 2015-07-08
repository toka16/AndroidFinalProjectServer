/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.model;

/**
 *
 * @author Administrator
 */
public class User {
    
    private int userID;
    private String name;
    private String surname;
    private String email;
    private String passowrd;
    private String phone;
    private String cardNumber;
    private String primaryNumber;

    
    public User(String name, String surname){
        this.name = name;
        this.surname = surname;
    }
    
    public void setID(int userID){
        this.userID = userID;
    }
    
    public int getID(){
        return userID;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setSurname(String newSurname){
        surname = newSurname;
    }
    
    public String getSurname(){
        return surname;
    }
    
    public void setPhone(String phone){
        this.phone = phone;
    }
    
    public String getPhone(){
        return phone;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getAddress(){
        return email;
    }
    
    public void setPassword(String password){
        this.passowrd = password;
    }
    
    public String getPassword(){
        return passowrd;
    }
    
    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;
    }
    
    public String getCardNumber(){
        return cardNumber;
    }
    
    public void setPrimaryNumber(String primaryNumber){
        this.primaryNumber = primaryNumber;
    }
    
    public String getPrimaryNumebr(){
        return primaryNumber;
    }
    
    @Override
    public String toString(){
        return name + " " + surname + " \n " + email;
    }
}
