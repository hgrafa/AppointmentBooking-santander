package com.hoogle.scheduller.domain.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class NewAppointmentDto {

    @NotBlank
    private String name;
    private String priority = "";
    @NotBlank
    private String start;
    @NotBlank
    private String finish;

}
