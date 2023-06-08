package com.svts.appointmentservice.repository;

import com.svts.appointmentservice.enums.AppointmentStatus;
import com.svts.appointmentservice.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepository  extends JpaRepository<Appointment,Long> {
    Appointment findByStatus(AppointmentStatus status);
    boolean existsByPatientIdAndStatus(Long patientId, AppointmentStatus status);
}
