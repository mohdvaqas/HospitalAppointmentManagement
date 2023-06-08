package com.svts.appointmentservice.controller.vm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.svts.appointmentservice.enums.AppointmentStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SearchAppointmentVM {

    @Nullable
    private Long appointmentId;

    @Nullable
    private Long patientId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateFrom;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateTo;

    @Nullable
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Nullable
    private String name;

    private Integer pageNumber = 0;

    @Positive
    private Integer pageSize;
}
