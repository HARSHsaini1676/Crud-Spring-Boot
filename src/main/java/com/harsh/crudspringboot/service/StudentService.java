package com.harsh.crudspringboot.service;

import com.harsh.crudspringboot.dto.CreateStudentRequestDto;
import com.harsh.crudspringboot.dto.CreateStudentResponseDto;
import com.harsh.crudspringboot.dto.UpdateStudentRequestDto;
import com.harsh.crudspringboot.dto.UpdateStudentResponseDto;
import com.harsh.crudspringboot.entity.Student;
import com.harsh.crudspringboot.exception.DuplicateResourceException;
import com.harsh.crudspringboot.exception.ResourceNotFoundException;
import com.harsh.crudspringboot.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;
    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public CreateStudentResponseDto createStudent(CreateStudentRequestDto studentReq){
        Student student = mapToEntity(studentReq);

        if(emailExists(student)) {
            throw new DuplicateResourceException("Student with email " + student.getEmail()
                    + " already exists");
        }

        studentRepository.save(student);

        CreateStudentResponseDto responseDto = mapToDto(student);

        return responseDto;
    }

    public CreateStudentResponseDto getStudent(Long id){

        Student studentResponse;
        studentResponse = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(()-> new ResourceNotFoundException("Resource with id " + id + " Not Found"));

        return  mapToDto(studentResponse);
    }

    public List<CreateStudentResponseDto> getAllStudent(){
        List<Student> studentList = studentRepository.findByDeletedIsFalse();

        return studentList.stream()
                .map(this::mapToDto)
                .toList();
    }

    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto studentRequest){
        Student existingStudent = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() ->
                new ResourceNotFoundException("Student with id " + id + " not found"));;

        Student student = mapToUpdateEntity(studentRequest, existingStudent);
        studentRepository.save(student);
        return mapToUpdateDto(student);
    }

    public void deleteStudent(Long id) {
        Student studentToBeDeleted = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " + id + " not found"));

        studentRepository.delete(studentToBeDeleted);
    }

    public void deleteStudentSoftly(Long id) {
        Student studentToBeDeleted = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " + id + " not found"));

        studentToBeDeleted.setDeleted(true);
        studentRepository.save(studentToBeDeleted);
    }

    private Student mapToEntity(CreateStudentRequestDto requestDto){
        Student student = new Student();

        student.setName(requestDto.getName());
        student.setEmail(requestDto.getEmail());
        student.setRollNo(requestDto.getRollNo());
        student.setSubject(requestDto.getSubject());
        student.setAge(requestDto.getAge());

        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        student.setDeleted(false);

        return student;
    }

    private CreateStudentResponseDto mapToDto(Student student){
        CreateStudentResponseDto responseDto = new CreateStudentResponseDto();

        responseDto.setName(student.getName());
        responseDto.setAge(student.getAge());
        responseDto.setEmail(student.getEmail());
        responseDto.setId(student.getId());
        responseDto.setRollNo(student.getRollNo());
        responseDto.setSubject(student.getSubject());
        responseDto.setCreatedAt(student.getCreatedAt());
        responseDto.setUpdatedAt(student.getUpdatedAt());

        return responseDto;
    }

    private Student mapToUpdateEntity(UpdateStudentRequestDto requestDto, Student student){

        student.setName(requestDto.getName());
        student.setRollNo(requestDto.getRollNo());
        student.setSubject(requestDto.getSubject());
        student.setAge(requestDto.getAge());
        student.setUpdatedAt(LocalDateTime.now());

        return student;
    }

    private UpdateStudentResponseDto mapToUpdateDto(Student student){
        UpdateStudentResponseDto responseDto = new UpdateStudentResponseDto();

        responseDto.setName(student.getName());
        responseDto.setAge(student.getAge());
        responseDto.setEmail(student.getEmail());
        responseDto.setId(student.getId());
        responseDto.setRollNo(student.getRollNo());
        responseDto.setSubject(student.getSubject());
        responseDto.setCreatedAt(student.getCreatedAt());
        responseDto.setUpdatedAt(student.getUpdatedAt());

        return responseDto;
    }

    private boolean emailExists(Student student) {
        return studentRepository.existsByEmailAndDeletedIsFalse(student.getEmail());
    }


}
