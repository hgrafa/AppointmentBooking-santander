package com.hoogle.scheduller.domain.model;

import com.hoogle.scheduller.domain.enums.Priority;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // primary key
    private String name;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private LocalDateTime start;
    private LocalDateTime finish;
    public boolean hasFinished() {
        return LocalDateTime.now().isAfter(finish);
    }

    // private Student student;

}
