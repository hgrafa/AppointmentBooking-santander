package com.hoogle.scheduller.service;

import com.hoogle.scheduller.model.Appointment;
import com.hoogle.scheduller.repository.AppointmentRepository;
import com.hoogle.scheduller.service.exceptions.InvalidMomentToScheduleException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    @NonNull
    private AppointmentRepository appointmentRepository;
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }
    public Appointment getById(long id) {
        return appointmentRepository.findById(id).get();
    }

    // casos:
    // 1. começo do novo depois do fim do já existente
    // 2. fim do novo antes do começo do já existente
    public Appointment register(Appointment newAppointment)
            throws InvalidMomentToScheduleException {
        var hasSpaceToSchedule = appointmentRepository
                .findAll()
                .stream()
                .filter(a -> !newAppointment.getStart().isAfter(a.getFinish()))
                .filter(a -> !newAppointment.getFinish().isBefore(a.getStart()))
                .toList()
                .isEmpty();

        if(!hasSpaceToSchedule)
            throw new InvalidMomentToScheduleException();

        return appointmentRepository.save(newAppointment);
    }

}
