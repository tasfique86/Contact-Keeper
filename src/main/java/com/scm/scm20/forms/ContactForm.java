package com.scm.scm20.forms;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {

    @NotBlank(message = "Name is require")
    private String   name;


    @Pattern(regexp = "^(?:\\+8801|01)[3-9]\\d{8}$", message = "Invalid Bangladeshi phone number!")
    private String   phoneNumber;

    @NotBlank(message = "Email is required ")
    @Email(message = "Invalid email [name@gmail.com ]")
    private String   email;

    private String   address;
    private String   about;
    private boolean favorite;
    private String   facebookLink;
    private String   linkedInLink;

    // version 2
    private String birthday;
    private String gender;
    @NotBlank(message = "Category is required")
    private String category;


    private MultipartFile profilePicture;
    private String pictureUrl;
}
