package com.scm.scm20.forms;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {

    private String   name;
    private String   phoneNumber;
    private String   email;
    private String   address;
    private String   about;
    private boolean favorite;
    private String   facebookLink;
    private String   linkedInLink;
    private MultipartFile profilePicture;
}
