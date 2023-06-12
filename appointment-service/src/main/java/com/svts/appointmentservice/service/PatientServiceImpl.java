package com.svts.appointmentservice.service;

import com.svts.appointmentservice.enums.AppointmentStatus;
import com.svts.appointmentservice.feign.PatientFeignClient;
import com.svts.appointmentservice.repository.spec.AppointmentRepositorySpec;
import com.svts.appointmentservice.service.dto.AppointmentDTO;
import com.svts.appointmentservice.service.dto.PatientAppointmentDTO;
import com.svts.appointmentservice.service.dto.PatientDTO;
import com.svts.appointmentservice.repository.PatientRepository;
import com.svts.appointmentservice.model.Appointment;
import com.svts.appointmentservice.model.Patient;
import com.svts.appointmentservice.controller.vm.PatientVM;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientFeignClient patientFeignClient;

    private final AppointmentRepositorySpec appointmentRepositorySpec;

    @Override
    public PatientDTO createPatient(PatientVM patientVM) {

        Patient patient = Patient.builder()
                .name(patientVM.getName())
                .email(patientVM.getEmail())
                .age(patientVM.getAge())
                .gender(patientVM.getGender())
                .build();

        patient = patientRepository.save(patient);

        return PatientDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .email(patient.getEmail())
                .age(patient.getAge())
                .gender(patient.getGender())
                .createdDate(patient.getCreatedDate())
                .build();
    }

    @Override
    public PatientAppointmentDTO getActivePatientAppointmentList(int patientId) {
        Patient patient = patientRepository.findById(String.valueOf(patientId)).get();
        return getPatientAppointmentDTO(patient);
    }

    private PatientAppointmentDTO getPatientAppointmentDTO (Patient patient) {
        List<Appointment> appointmentList = patient.getAppointment().stream().filter(appointment -> appointment.getStatus().equals(AppointmentStatus.ACTIVE)).toList();

        PatientAppointmentDTO patientAppointmentDTO = new PatientAppointmentDTO();

        patientAppointmentDTO.setIdentityNumber(patient.getId());
        patientAppointmentDTO.setName(patient.getName());
        patientAppointmentDTO.setEmail(patient.getEmail());
        patientAppointmentDTO.setCreatedDate(patient.getCreatedDate());

        List<AppointmentDTO> appointmentDTOList = new ArrayList<>();

        for (Appointment appointment : appointmentList) {
            AppointmentDTO appointmentDTO = new AppointmentDTO();
            appointmentDTO.setId(appointment.getId());
            appointmentDTO.setStatus(appointment.getStatus().name());
            appointmentDTO.setAppointmentDateTime(appointment.getAppointmentDateTime());
            appointmentDTO.setCreatedDate(appointment.getCreatedDate());
            appointmentDTOList.add(appointmentDTO);
        }

        patientAppointmentDTO.setAppointments(appointmentDTOList);
        return patientAppointmentDTO;
    }
}
