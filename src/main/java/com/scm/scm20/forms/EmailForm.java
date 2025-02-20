package com.scm.scm20.forms;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailForm {

    @NotBlank(message = "Email is required ")
    @Email(message = "Invalid email [name@gmail.com ]")
    private String to;

    private String subject;
    private String body;
    private MultipartFile attachment;

    private String time;
    private String date;

}
