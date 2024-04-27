package com.ClearSolutions.testassig;


import jakarta.persistence.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Validated
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // r (required)
    private String email; // r
    private String firstName; // r
    private String lastName; // r
    private String birthDate;  // r
    private String address;  // o (optional)
    private String phoneNumber;  // o

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public User(long id, String email, String firstName, String lastName, String birthDate, String address, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //=================================================================================== Helpful methods
    public int getAge(){
        LocalDate birthDate = LocalDate.parse(this.birthDate); // convert to date from String
        LocalDate currentDate = LocalDate.now(); //local date
        Period ageDifference = Period.between(birthDate, currentDate); // period

        return ageDifference.getYears();

    }

    public String checkFields(){
        String checkMessage = "fine";
        if(this.email == null || this.email.isEmpty()){
            checkMessage = "error: email is null or empty";
        }
        if(this.firstName == null || this.firstName.isEmpty()){
            checkMessage = "error: firstName is null or empty";
        }

        if(this.lastName == null || this.lastName.isEmpty()){
            checkMessage = "error: lastName is null or empty";
        }
        if(this.birthDate == null || this.birthDate.isEmpty()){
            checkMessage = "error: birthDate is null or empty";
        }
        return checkMessage;
    }

    public final String validEmail(){
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(this.email);

        if(!matcher.matches()){
            return "wrong validation email";
        }
        return "fine";
    }

}
