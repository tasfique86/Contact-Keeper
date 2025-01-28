package com.scm.scm20.forms;

import lombok.*;
import org.springframework.stereotype.Component;

import java.lang.String;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@ToString
public class UserForm {
    private String name;
    private String email;
    private String password;
    private String about;
    private String phoneNumber;

//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setAbout(String about) {
//        this.about = about;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public String getAbout() {
//        return about;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
}
