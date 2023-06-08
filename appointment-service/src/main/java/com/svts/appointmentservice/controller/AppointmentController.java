package com.svts.appointmentservice.controller;

import com.svts.appointmentservice.controller.vm.SearchAppointmentVM;
import com.svts.appointmentservice.service.AppointmentService;
import com.svts.appointmentservice.controller.vm.CreateUpdateAppointmentVM;
import com.svts.appointmentservice.service.PatientService;
import com.svts.appointmentservice.service.dto.AppointmentDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;

    public AppointmentController(AppointmentService appointmentService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;

    }

    @PostMapping("/appointment")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody @Valid CreateUpdateAppointmentVM createUpdateAppointmentVM) {
       return new ResponseEntity<>(appointmentService.createAppointment(createUpdateAppointmentVM), HttpStatus.OK);
    }

    @PatchMapping("/appointment")
    public ResponseEntity<AppointmentDTO> updateAppointment(@RequestBody @Valid CreateUpdateAppointmentVM createUpdateAppointmentVM) {
        return new ResponseEntity<>(appointmentService.updateAppointment(createUpdateAppointmentVM), HttpStatus.OK);
    }

    @DeleteMapping("/appointmentRemove")
    public boolean appointmentRemove(@RequestParam(name = "appointmentId") Long appointmentId){
        return appointmentService.appointmentRemove(appointmentId);
    }

    @GetMapping(path = "/getAllAppointments")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments(@RequestBody @Valid SearchAppointmentVM searchAppointmentVM){
        return new ResponseEntity<>(appointmentService.getAllAppointments(searchAppointmentVM), HttpStatus.OK);
    }
}
