package com.mobileteacherstudent.application.tutor.repository;

import com.mobileteacherstudent.application.tutor.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor,Long> {
    Optional<Tutor> findByUsername(String username);
    Optional<Tutor> findByUsernameAndPassword(String username,String password);
    Optional<Tutor> findByFields(String id);
}
