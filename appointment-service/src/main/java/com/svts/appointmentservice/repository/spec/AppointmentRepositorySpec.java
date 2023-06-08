package com.svts.appointmentservice.repository.spec;

import com.svts.appointmentservice.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface AppointmentRepositorySpec extends JpaRepository<Appointment,Long>, JpaSpecificationExecutor {
    @Override
    Page<Appointment> findAll(Pageable pageable);

}
