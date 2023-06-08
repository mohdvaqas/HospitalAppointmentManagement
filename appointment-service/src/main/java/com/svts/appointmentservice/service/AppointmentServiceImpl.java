package com.svts.appointmentservice.service;

import com.svts.appointmentservice.controller.vm.CreateUpdateAppointmentVM;
import com.svts.appointmentservice.controller.vm.SearchAppointmentVM;
import com.svts.appointmentservice.enums.AppointmentStatus;
import com.svts.appointmentservice.exception.NotAvailableException;
import com.svts.appointmentservice.repository.AppointmentRepository;
import com.svts.appointmentservice.repository.PatientRepository;
import com.svts.appointmentservice.repository.spec.AppointmentRepositorySpec;
import com.svts.appointmentservice.repository.spec.AppointmentSpec;
import com.svts.appointmentservice.service.dto.AppointmentDTO;
import com.svts.appointmentservice.model.Appointment;
import com.svts.appointmentservice.model.Patient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepositorySpec appointmentRepositorySpec;

    @Override
    public AppointmentDTO createAppointment(CreateUpdateAppointmentVM createUpdateAppointmentVM) {

        Patient patient = patientRepository.findById(String.valueOf(createUpdateAppointmentVM.getPatientId()))
                .orElseThrow(() -> new NotAvailableException("Patient not found !"));

        boolean appointmentAlreadyExists = appointmentRepository.existsByPatientIdAndStatus(createUpdateAppointmentVM.getPatientId(), AppointmentStatus.ACTIVE);

        if (appointmentAlreadyExists) {
            throw new NotAvailableException("Cannot schedule, another appointment already exists");
        }

        Appointment appointment = new Appointment();
        appointment.setAppointmentDateTime(createUpdateAppointmentVM.getAppointmentDateTime());
        appointment.setStatus(AppointmentStatus.ACTIVE);
        appointment.setReason(createUpdateAppointmentVM.getReason());
        appointment.setCreatedDate(new Date());
        appointment.setLastModifiedDate(new Date());
        appointment.setPatient(patient);
        appointment = appointmentRepository.save(appointment);

        return AppointmentDTO.builder()
                .appointmentDateTime(appointment.getAppointmentDateTime())
                .id(appointment.getId())
                .status(String.valueOf(appointment.getStatus()))
                .createdDate(appointment.getCreatedDate())
                .lastModifiedDate(appointment.getLastModifiedDate())
                .build();
    }

    @Override
    public boolean appointmentRemove(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
        return true;
    }

    @Override
    public AppointmentDTO updateAppointment(CreateUpdateAppointmentVM createUpdateAppointmentVM) {

        if (null == createUpdateAppointmentVM.getAppointmentId())
            throw new NotAvailableException("Appointment id doesn't exists");

        Appointment appointment = appointmentRepository.findById(createUpdateAppointmentVM.getAppointmentId())
                .orElseThrow(() -> new NotAvailableException("Appointment doesn't exists"));

        appointment.setAppointmentDateTime(createUpdateAppointmentVM.getAppointmentDateTime());
        appointment.setStatus(createUpdateAppointmentVM.getStatus());
        appointment.setReason(createUpdateAppointmentVM.getReason());
        appointment.setPatient(appointment.getPatient());
        appointment.setLastModifiedDate(new Date());
        appointment = appointmentRepository.save(appointment);

        return AppointmentDTO.builder()
                .appointmentDateTime(appointment.getAppointmentDateTime())
                .id(appointment.getId())
                .status(String.valueOf(appointment.getStatus()))
                .createdDate(appointment.getCreatedDate())
                .reason(appointment.getReason())
                .build();
    }

    private AppointmentDTO getAppointmentDTO(Appointment appointment) {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(appointment.getId());
        appointmentDTO.setStatus(appointment.getStatus().name());
        appointmentDTO.setAppointmentDateTime(appointment.getAppointmentDateTime());
        appointmentDTO.setCreatedDate(appointment.getCreatedDate());
        appointmentDTO.setLastModifiedDate(appointment.getLastModifiedDate());
        appointmentDTO.setReason(appointment.getReason());
        return appointmentDTO;
    }

    @Override
    public List<AppointmentDTO> getAllAppointments(SearchAppointmentVM searchAppointmentVM) {
        /*
         *************************************************************************
         * Create the search specification using the patient appointment
         *************************************************************************
         */
        Pageable pageRequest = PageRequest.of(searchAppointmentVM.getPageNumber(), searchAppointmentVM.getPageSize());
        AppointmentSpec appointmentSpec = new AppointmentSpec(searchAppointmentVM);
        List<Appointment> appointmentList = null;
        /*
         ******************************************************************
         * Find all requests based on user's input and store then in a list
         ******************************************************************
         */
        Page<?> appointmentPageSpec = appointmentRepositorySpec.findAll(appointmentSpec, pageRequest);
        List<Appointment> transactionPageContent = (List<Appointment>) appointmentPageSpec.getContent();
        appointmentList = new ArrayList<>(transactionPageContent);

        return appointmentList
                .stream()
                .map(this::getAppointmentDTO)
                .toList();
    }

}
