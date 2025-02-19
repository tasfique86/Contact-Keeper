package com.scm.scm20.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailFormForMultiple {


    @NotEmpty(message = "Email list cannot be empty")
    private String[] to;  // No @NotBlank on an array!

    private String subject;
    private String body;
}
