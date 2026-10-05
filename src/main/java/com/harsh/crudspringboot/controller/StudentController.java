package com.harsh.crudspringboot.controller;


import com.harsh.crudspringboot.dto.CreateStudentRequestDto;
import com.harsh.crudspringboot.dto.CreateStudentResponseDto;
import com.harsh.crudspringboot.dto.UpdateStudentRequestDto;
import com.harsh.crudspringboot.dto.UpdateStudentResponseDto;
//import com.harsh.crudspringboot.entity.Student;
import com.harsh.crudspringboot.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    // create


    @PostMapping
    public ResponseEntity<CreateStudentResponseDto> createStudent(@Valid @RequestBody CreateStudentRequestDto requestDto){
        CreateStudentResponseDto responseDto = studentService.createStudent(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    // read

    @GetMapping("/{id}")
    public ResponseEntity<CreateStudentResponseDto> getStudent(@PathVariable Long id){
        CreateStudentResponseDto studentResponse = studentService.getStudent(id);

        return ResponseEntity.ok(studentResponse);
    }

    @GetMapping
    public ResponseEntity<List<CreateStudentResponseDto>> getAllStudent(){
        List<CreateStudentResponseDto> studentsResponse = studentService.getAllStudent();
        if(studentsResponse.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(studentsResponse);
    }

// update


    @PutMapping
    public ResponseEntity<UpdateStudentResponseDto> updateStudent(@RequestBody UpdateStudentRequestDto studentRequest, @RequestParam Long id){
        UpdateStudentResponseDto updatedStudent = studentService.updateStudent(id, studentRequest);

        return ResponseEntity.ok(updatedStudent);
    }

    //delete

    @DeleteMapping
    public ResponseEntity<String> deleteStudent(@RequestParam Long id) {
        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/delete-soft")
    public ResponseEntity<String> deleteStudentSoftly(@RequestParam Long id) {
        studentService.deleteStudentSoftly(id);

        return ResponseEntity.noContent().build();
    }

}
