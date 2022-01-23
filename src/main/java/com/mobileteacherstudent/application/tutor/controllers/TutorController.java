package com.mobileteacherstudent.application.tutor.controllers;

import com.mobileteacherstudent.application.fields.entity.Field;
import com.mobileteacherstudent.application.fields.repository.FieldRepository;
import com.mobileteacherstudent.application.tutor.entity.Tutor;
import com.mobileteacherstudent.application.tutor.models.LoginCredentials;
import com.mobileteacherstudent.application.tutor.models.TutorModel;
import com.mobileteacherstudent.application.tutor.repository.TutorRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/tutor")
public class TutorController {
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private FieldRepository fieldRepository;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody TutorModel tutorModel){
        List<Field> fields = new ArrayList<>();
        tutorModel.getFields().forEach(id ->{
            Optional<Field> field = fieldRepository.findById(id);
            field.ifPresent(fields::add);
        });
        if(tutorRepository.findByUsername(tutorModel.getUsername()).isPresent()){
            return ResponseEntity.ok("Login zajęty!");
        }
        else{
            Tutor tutor = tutorRepository.save(Tutor.tutor(tutorModel, fields));
            fields.forEach(field -> {
                field.getTutors().add(tutor);
                fieldRepository.save(field);
            });
            return ResponseEntity.ok("Pomyślnie stworzono korepetytora");
        }
    }

    @GetMapping
    public ResponseEntity<?> login(@RequestBody LoginCredentials credentials){
        Optional<Tutor> tutor = tutorRepository.findByUsernameAndPassword(credentials.getUsername(), Base64.getEncoder().encodeToString(credentials.getPassword().getBytes()));
        if (tutor.isPresent()){
            return ResponseEntity.ok(tutor.get().login());
        }
        else return ResponseEntity.ok("Złe dane logowania!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findTutor(@PathVariable Long id){
        Optional<Tutor> tutor = tutorRepository.findById(id);
        if(tutor.isPresent()){
            return ResponseEntity.ok(tutor.get().tutorDTO());
        }
        else return ResponseEntity.ok("Nie ma korepetytora o id: " + id + "!");
    }

    @GetMapping("/findTutors/{field}")
    public ResponseEntity<?> findTutors(@PathVariable String field){
        List<Tutor> tutors = tutorRepository.findAll();
        List<Tutor> filteredTutors = new ArrayList<>();
        tutors.forEach(tutor ->
            tutor.getFields().forEach(field1 ->{
                if(field1.getName().equals(field)) filteredTutors.add(tutor.tutorDTO());
            }));
        if (!filteredTutors.isEmpty()){
            return ResponseEntity.ok(filteredTutors);
        }
        else return ResponseEntity.ok("Nie ma korepetytorów");
    }

    @GetMapping("/findTutors")
    public ResponseEntity<?> findTutors(){
        List<Tutor> tutors = tutorRepository.findAll();
        if (!tutors.isEmpty()){
            return ResponseEntity.ok(tutors.stream().map(Tutor::tutorDTO));
        }
        else return ResponseEntity.ok("Nie ma korepetytorów");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> tutorDELETE(@PathVariable Long id){
        System.out.println(id);
        Optional<Tutor> tutor = tutorRepository.findById(id);
        if (tutor.isPresent()){
            tutorRepository.delete(tutor.get());
            return ResponseEntity.ok("Pomyślnie usunięto korepetytora !");
        }
        else return ResponseEntity.ok("Nie ma korepetytora o takim ID: " + id + "!");
    }




}
