package com.scm.scm20.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailFormForMultiple {


    @NotNull(message = "Email list cannot be empty")
    private String[] to;  // No @NotBlank on an array!

    private String subject;
    private String body;
}
