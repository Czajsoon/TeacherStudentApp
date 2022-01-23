package com.mobileteacherstudent.application.tutor.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mobileteacherstudent.application.fields.entity.Field;
import com.mobileteacherstudent.application.tutor.models.TutorModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Base64;
import java.util.List;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Column
    private String phoneNumber;
    @Column
    private String description;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Field_Tutor",
            joinColumns = { @JoinColumn(name = "field_id") },
            inverseJoinColumns = { @JoinColumn(name = "tutor_id") }
    )
    private List<Field> fields;

    public static Tutor tutor(TutorModel tutorModel,List<Field> fields){
        Tutor tutor = new Tutor();
        tutor.setUsername(tutorModel.getUsername());
        tutor.setName(tutorModel.getName());
        tutor.setSurname(tutorModel.getSurname());
        tutor.setPassword(Base64.getEncoder().encodeToString(tutorModel.getPassword().getBytes()));
        tutor.setDescription(tutorModel.getDescription());
        tutor.setEmail(tutorModel.getEmail());
        tutor.setPhoneNumber(tutorModel.getPhoneNumber());
        tutor.setFields(fields);
        return tutor;
    }

    public Tutor login(){
        this.getFields().forEach(Field::fieldDto);
        return this;
    }

    public Tutor tutorDTO(){
        this.getFields().forEach(Field::fieldDto);
        this.setUsername(null);
        this.setPassword(null);
        return this;
    }
}
