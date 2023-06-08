package com.svts.appointmentservice.service;

import com.svts.appointmentservice.controller.vm.PatientVM;
import com.svts.appointmentservice.service.dto.PatientAppointmentDTO;
import com.svts.appointmentservice.service.dto.PatientDTO;

public interface PatientService {

    PatientDTO createPatient(PatientVM patientVM);

    PatientAppointmentDTO getActivePatientAppointmentList(int patientId);
}
