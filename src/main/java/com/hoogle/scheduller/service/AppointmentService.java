package com.hoogle.scheduller.service;

import com.hoogle.scheduller.domain.dto.AppointmentShortViewDto;
import com.hoogle.scheduller.domain.dto.AppointmentViewDto;
import com.hoogle.scheduller.domain.dto.NewAppointmentDto;
import com.hoogle.scheduller.domain.model.Appointment;
import com.hoogle.scheduller.domain.enums.Priority;
import com.hoogle.scheduller.repository.AppointmentRepository;
import com.hoogle.scheduller.service.exceptions.InvalidMomentToScheduleException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    @NonNull
    private AppointmentRepository appointmentRepository;
    public List<?> getAll(boolean shortMode) {
        var appointmentStream = appointmentRepository.findAll().stream();
        return (shortMode) ?
                appointmentStream.map(AppointmentShortViewDto::new).toList() :
                appointmentStream.map(AppointmentViewDto::new).toList() ;
    }
    public Appointment getById(long id) {
        return appointmentRepository.findById(id).get();
    }

    // casos:
    // 1. começo do novo depois do fim do já existente
    // 2. fim do novo antes do começo do já existente
    public Appointment register(NewAppointmentDto newAppointment)
            throws InvalidMomentToScheduleException {

        var toRegister  = toAppointment(newAppointment);

        var hasSpaceToSchedule = appointmentRepository
                .findAll()
                .stream()
                .filter(a -> !toRegister.getStart().isAfter(a.getFinish()))
                .filter(a -> !toRegister.getFinish().isBefore(a.getStart()))
                .toList()
                .isEmpty();

        if(!hasSpaceToSchedule)
            throw new InvalidMomentToScheduleException();

        return appointmentRepository.save(toRegister);
    }
    private Appointment toAppointment(NewAppointmentDto appointmentForm) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Function<String, Priority> priorityParser = (priorityText) ->
            switch (priorityText.toUpperCase()) {
                case "HIGH" -> Priority.HIGH;
                case "MEDIUM" -> Priority.MEDIUM;
                case "LOW" -> Priority.LOW;
                default -> Priority.NONE;
            };

        return Appointment
                .builder()
                .name(appointmentForm.getName())
                .priority(priorityParser.apply(appointmentForm.getPriority()))
                .start(LocalDateTime.parse(appointmentForm.getStart(), formatter))
                .finish(LocalDateTime.parse(appointmentForm.getFinish(), formatter))
                .build();
    }

    private <T> T getNewValueIfNotNull(T pastValue, T newValue) {
        return newValue == null ? pastValue : newValue ;
    }


}
