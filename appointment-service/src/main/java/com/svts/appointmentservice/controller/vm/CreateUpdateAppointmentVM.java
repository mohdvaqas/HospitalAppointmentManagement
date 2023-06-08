package com.svts.appointmentservice.controller.vm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.svts.appointmentservice.enums.AppointmentStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateUpdateAppointmentVM {

    @Nullable
    private Long appointmentId;

    @NotNull
    private Long patientId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appointmentDateTime;

    @Nullable
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Nullable
    private String reason;
}
