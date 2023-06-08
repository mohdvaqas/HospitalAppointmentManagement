package com.svts.appointmentservice.service.dto;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDTO {

    private long id;
    private String name;
    private String email;
    private int age;
    private int gender;
    private Date createdDate;

}
