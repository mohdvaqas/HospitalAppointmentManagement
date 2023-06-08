package com.svts.appointmentservice.service;

import com.svts.appointmentservice.controller.vm.CreateUpdateAppointmentVM;
import com.svts.appointmentservice.controller.vm.SearchAppointmentVM;
import com.svts.appointmentservice.service.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService {

   AppointmentDTO createAppointment(CreateUpdateAppointmentVM createUpdateAppointmentVM);

   AppointmentDTO updateAppointment(CreateUpdateAppointmentVM createUpdateAppointmentVM);

   boolean appointmentRemove(Long appointmentId);

   List<AppointmentDTO> getAllAppointments(SearchAppointmentVM searchAppointmentVM);
}
