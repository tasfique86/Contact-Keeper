package com.scm.scm20.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.lang.String;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString



public class UserForm {





    @NotBlank(message = "Username is required")
    @Size(min=3,message = "Minimun 3 Character is required")
    private String name;

    @Email(message = "Invalid Email Address")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Minimum 6 character is required")
    private String password;

    @NotBlank(message = "About is required")
    private String about;

    @Size(max=20)
    private String address;


    @Size(min = 8 ,max = 12, message = "Invalid Phone Number")
    private String phoneNumber;


    private String profilePictureURL;
    private MultipartFile profilePictureFile;
    private String birthday;




}
