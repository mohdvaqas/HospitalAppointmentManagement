package com.svts.appointmentservice.controller.vm;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PatientVM {

    @NotEmpty
    @Pattern(regexp="^[a-zA-Z0-9 ]{3,}$",message="Incorrect format")
    private String name;

    @Email
    @NotEmpty
    private String email;

    @NotNull
    private Integer age;

    @NotNull
    private Integer gender;



}
