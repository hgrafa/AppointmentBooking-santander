package com.hoogle.scheduller.domain.dto;

import com.hoogle.scheduller.domain.model.Appointment;
import lombok.*;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AppointmentViewDto {
    private long id;
    private String name;
    private String priority;
    private String start;
    private String finish;
    private boolean finished;

    public AppointmentViewDto(Appointment appointment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        this.name = appointment.getName();
        this.priority = appointment.getPriority().toString().toLowerCase();
        this.id = appointment.getId();
        this.start = formatter.format(appointment.getStart());
        this.finish = formatter.format(appointment.getFinish());
        this.finished = appointment.hasFinished();
    }
}
