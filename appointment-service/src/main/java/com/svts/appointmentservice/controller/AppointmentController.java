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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;

    public AppointmentController(AppointmentService appointmentService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;

    }

    @PostMapping("/create")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody @Valid CreateUpdateAppointmentVM createUpdateAppointmentVM) {
       return new ResponseEntity<>(appointmentService.createAppointment(createUpdateAppointmentVM), HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity<AppointmentDTO> updateAppointment(@RequestBody @Valid CreateUpdateAppointmentVM createUpdateAppointmentVM) {
        return new ResponseEntity<>(appointmentService.updateAppointment(createUpdateAppointmentVM), HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Map<String, Boolean>> appointmentRemove(@RequestParam(name = "appointmentId") Long appointmentId){
        Map<String, Boolean> appointmentPayload = new HashMap<>();
        boolean appointment = appointmentService.appointmentRemove(appointmentId);
        appointmentPayload.put("deleted", appointment);
        return new ResponseEntity<>(appointmentPayload, HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments(@RequestBody @Valid SearchAppointmentVM searchAppointmentVM){
        return new ResponseEntity<>(appointmentService.getAllAppointments(searchAppointmentVM), HttpStatus.OK);
    }
}
