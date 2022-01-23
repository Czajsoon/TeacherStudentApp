package com.mobileteacherstudent.application.fields.controllers;

import com.mobileteacherstudent.application.fields.entity.Field;
import com.mobileteacherstudent.application.fields.models.FieldModel;
import com.mobileteacherstudent.application.fields.models.FieldModelLocal;
import com.mobileteacherstudent.application.fields.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Optional;

@RestController
@RequestMapping("/field_tutor")
public class FieldController {
    @Autowired
    private FieldRepository fieldRepository;

    @PostMapping("/local")
    private ResponseEntity<?> fieldLocal(@RequestBody FieldModelLocal fieldModel){
        Field save = fieldRepository.save(Field.fieldLocal(fieldModel));
        return ResponseEntity.ok(save);
    }

    @PostMapping
    public ResponseEntity<?> fieldPOST(@RequestBody FieldModel fieldModel){
        Field save = fieldRepository.save(Field.field(fieldModel));
        Optional<Field> field = fieldRepository.findById(save.getId());
        if(field.isPresent()){
            return ResponseEntity.ok("Pomyślnie dodano: " + field.get().getName());
        }
        else return ResponseEntity.ok("Wystąpił błąd z dodawaniem elementu: " + fieldModel);
    }

    @GetMapping
    public ResponseEntity<?> fieldGET(){
        return ResponseEntity.ok(fieldRepository.findAll().stream().map(Field::fieldDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fieldGET(@PathVariable Long id){
        Optional<Field> field = fieldRepository.findById(id);
        if(field.isPresent()){
            return ResponseEntity.ok(field.get().fieldDto());
        }
        else return ResponseEntity.ok("Nie ma takigo elmentu o ID: " + id +" !");
    }

    @PutMapping
    public ResponseEntity<?> fieldPUT(@RequestBody FieldModel fieldModel){
        Optional<Field> field = fieldRepository.findById(fieldModel.getId());
        if(field.isPresent()){
            field.get().setName(fieldModel.getName());
            fieldRepository.save(field.get());
            return ResponseEntity.ok("Pomyślnie zmieniono element!");
        }
        else return ResponseEntity.ok("Nie ma elementu o takim ID: " + fieldModel.getId() + "!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> fieldDELETE(@PathVariable Long id){
        Optional<Field> field = fieldRepository.findById(id);
        if (field.isPresent()){
            fieldRepository.delete(field.get());
            return ResponseEntity.ok("Pomyślnie usunięto: " + field.get().getName());
        }
        else return ResponseEntity.ok("Nie ma elementu o takim ID: " + id + "!");
    }

//    @PostConstruct
//    private void init(){
//        fieldLocal(FieldModelLocal.builder().name("C++").build());
//        fieldLocal(FieldModelLocal.builder().name("Java").build());
//        fieldLocal(FieldModelLocal.builder().name("C#").build());
//        fieldLocal(FieldModelLocal.builder().name("RUBY").build());
//        fieldLocal(FieldModelLocal.builder().name("PYTHON").build());
//    }

}
