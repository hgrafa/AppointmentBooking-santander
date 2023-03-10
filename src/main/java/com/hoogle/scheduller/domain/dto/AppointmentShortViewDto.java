package com.hoogle.scheduller.domain.dto;

import com.hoogle.scheduller.domain.model.Appointment;
import lombok.*;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AppointmentShortViewDto {
    private String name;
    private String start;
    private String finish;

    public AppointmentShortViewDto(Appointment appointment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.name = appointment.getName();
        this.start = formatter.format(appointment.getStart());
        this.finish = formatter.format(appointment.getFinish());
    }
}
