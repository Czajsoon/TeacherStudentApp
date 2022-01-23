package com.mobileteacherstudent.application.fields.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mobileteacherstudent.application.fields.models.FieldModel;
import com.mobileteacherstudent.application.fields.models.FieldModelLocal;
import com.mobileteacherstudent.application.tutor.entity.Tutor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "fields")
    private List<Tutor> tutors;

    public static Field fieldLocal(FieldModelLocal fieldModel){
        Field field = new Field();
        field.setName(fieldModel.getName());
        return field;
    }

    public static Field field(FieldModel fieldModel){
        Field field = new Field();
        field.setName(fieldModel.getName());
        return field;
    }

    public Field fieldDto(){
        this.setTutors(null);
        return this;
    }
}
