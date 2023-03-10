package com.hoogle.scheduller.resources.api;

import com.hoogle.scheduller.domain.dto.NewAppointmentDto;
import com.hoogle.scheduller.domain.model.Appointment;
import com.hoogle.scheduller.service.AppointmentService;
import com.hoogle.scheduller.service.exceptions.InvalidMomentToScheduleException;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/0.0.1/appointments")
@RequiredArgsConstructor
public class AppointmentRestController {

    @NonNull
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<?>> getAllAppointments(
            @RequestParam(defaultValue = "true") boolean shortMode
    ) {
        var appointments = appointmentService.getAll(shortMode);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("{id}")
    public ResponseEntity<Appointment> getAppointmentBydId(@PathVariable long id){
        var appointment = appointmentService.getById(id);
        return ResponseEntity.ok(appointment);
    }

    @PostMapping
    public ResponseEntity<?> registerAppointment(
            @Valid @RequestBody NewAppointmentDto newAppointment
    ) {
        try {
            var appointmentSchedule = appointmentService.register(newAppointment);
            return ResponseEntity.ok(appointmentSchedule);
        } catch (InvalidMomentToScheduleException exception) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
        }

    }
}