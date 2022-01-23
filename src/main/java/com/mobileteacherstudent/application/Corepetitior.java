package com.mobileteacherstudent.application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Corepetitior {
    @Id
    private Long id;

    @Column
    private String name;
}
