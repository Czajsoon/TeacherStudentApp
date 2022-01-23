package com.mobileteacherstudent.application.tutor.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TutorModel {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String description;
    private List<Long> fields;
}
