package com.svts.appointmentservice.service.dto;

import lombok.*;

import java.util.Date;
import java.util.List;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientAppointmentDTO {
    private Long identityNumber;
    private String name;
    private String email;
    private List<AppointmentDTO> appointmentResponse;
    private Date createdDate;
}
