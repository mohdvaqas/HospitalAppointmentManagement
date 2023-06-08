package com.svts.appointmentservice.controller;

import com.svts.appointmentservice.service.PatientService;
import com.svts.appointmentservice.controller.vm.PatientVM;
import com.svts.appointmentservice.service.dto.PatientAppointmentDTO;
import com.svts.appointmentservice.service.dto.PatientDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/patient")
@Slf4j
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    @GetMapping(path = "/getAllActiveAppointments/{patientId}")
    public ResponseEntity<PatientAppointmentDTO> getActivePatientAppointmentList(@PathVariable int patientId){
       return new ResponseEntity<>(patientService.getActivePatientAppointmentList(patientId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PatientDTO> createPatient(@RequestBody @Valid PatientVM patientVM) {
        return new ResponseEntity<>(patientService.createPatient(patientVM), HttpStatus.OK);
    }

}
