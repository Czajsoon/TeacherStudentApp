package com.mobileteacherstudent.application.fields.repository;

import com.mobileteacherstudent.application.fields.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field,Long> {
}
