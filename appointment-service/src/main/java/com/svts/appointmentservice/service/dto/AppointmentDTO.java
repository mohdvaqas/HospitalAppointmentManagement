package com.svts.appointmentservice.service.dto;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    private Long id;
    private String status;
    private Date appointmentDateTime;
    private Date createdDate;
    private Date lastModifiedDate;
    private String reason;
}

