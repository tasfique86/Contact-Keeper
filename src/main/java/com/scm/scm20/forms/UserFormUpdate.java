package com.scm.scm20.forms;

import com.scm.scm20.entities.Providers;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString



public class UserFormUpdate {


    @NotBlank
    private String userId;

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


    private boolean enabled=false;
    private boolean emailVerified=false;
    private boolean phoneNumberVerified=false;

    private String profilePictureURL;
    private MultipartFile profilePictureFile;
    private String birthday;


    @Enumerated(value = EnumType.STRING)
    //SELF,GOOGLE,FACEBOOK,TWITTER,LINKEDIN,GITHUB
    private Providers providers=Providers.SELF;
    private String providerUserId;
}
