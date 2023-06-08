package com.svts.appointmentservice.model;

import com.svts.appointmentservice.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "appointment")
@EntityListeners(AuditingEntityListener.class)
public class Appointment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Date appointmentDateTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;
}
