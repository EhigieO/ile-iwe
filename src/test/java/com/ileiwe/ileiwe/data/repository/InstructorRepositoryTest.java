package com.ileiwe.ileiwe.data.repository;

import com.ileiwe.ileiwe.data.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = ("/db/insert.sql"))
class InstructorRepositoryTest {

    @Autowired
    InstructorRepository instructorRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveInstructorAsLearningPartyTest(){
        LearningParty user = new LearningParty("trainer@ileiwe.com", "123pass", new Authority(Role.ROLE_INSTRUCTOR));

        Instructor instructor  = Instructor.builder().firstname("John").lastname("Alao").learningParty(user).build();

        log.info("Instructor before saving --> {}", instructor);
        instructorRepository.save(instructor);
        assertThat(instructor.getId()).isNotNull();
        assertThat(instructor.getLearningParty().getId()).isNotNull();
        log.info("Instructor after saving --> {}", instructor);
    }

    @Test
    void instructorWithNullValuesTest(){
        LearningParty user = new LearningParty("trainer1@ileiwe.com", "1234pass", new Authority(Role.ROLE_INSTRUCTOR));

        Instructor instructor  = Instructor.builder().firstname(null).lastname(null).learningParty(user).build();

        assertThrows(ConstraintViolationException.class, () -> instructorRepository.save(instructor));
    }

    @Test
    void instructorWithEmptyStringAndWhiteSpaceTest(){
        LearningParty user = new LearningParty("trainer2@ileiwe.com", "12345pass", new Authority(Role.ROLE_INSTRUCTOR));

        Instructor instructor  = Instructor.builder().firstname(null).lastname(null).learningParty(user).build();

        assertThrows(ConstraintViolationException.class, () -> instructorRepository.save(instructor));
    }


    @Test
    void createInstructorUniqueEmailsTest() {
        //given
        LearningParty user = new LearningParty("trainer3@ileiwe.com", "123456pass", new Authority(Role.ROLE_INSTRUCTOR));

        Instructor instructor  = Instructor.builder().firstname("Shade").lastname("Bajo").learningParty(user).build();

        instructorRepository.save(instructor);

        assertThat(instructor.getId()).isNotNull();
        assertThat(instructor.getLearningParty().getEmail()).isEqualTo("trainer3@ileiwe.com");
        assertThat(instructor.getLearningParty().getAuthorities().get(0).getAuthority()).isEqualTo(Role.ROLE_INSTRUCTOR);
        assertThat(instructor.getLearningParty().getAuthorities().get(0).getId()).isNotNull();

        //when
        LearningParty user2 = new LearningParty("trainer3@ileiwe.com", "123456pass", new Authority(Role.ROLE_INSTRUCTOR));

        Instructor instructor2  = Instructor.builder().firstname("Shade").lastname("Bajo").learningParty(user2).build();

        assertThrows(DataIntegrityViolationException.class, () -> instructorRepository.save(instructor2));

    }

    @Test
    void updateInstructorTest(){
        LearningParty user = new LearningParty("trainer5@ileiwe.com", "124pass", new Authority(Role.ROLE_INSTRUCTOR));

        Instructor instructor  = Instructor.builder().firstname("Johnie").lastname("Alamies").learningParty(user).build();


        log.info("Instructor before saving --> {}", instructor);
        instructorRepository.save(instructor);
        assertThat(instructor.getId()).isNotNull();
        assertThat(instructor.getLearningParty().getId()).isNotNull();
        log.info("Instructor after saving --> {}", instructor);

        instructor.setBio("i like wahala, but i can be very sweet");
        instructor.setSpecialization("talking");

        instructorRepository.save(instructor);

        Optional<Instructor> foundInstuctor = instructorRepository.findById(instructor.getId());

        assertThat(instructor.getBio()).isEqualTo(foundInstuctor.get().getBio());

    }

    @Test
    void updateInstructorAfterCreateTest() {
        LearningParty user = new LearningParty("trainer6@ileiwe.com", "124pass", new Authority(Role.ROLE_INSTRUCTOR));

        Instructor instructor = Instructor.builder().firstname("Johnie").lastname("Alamies").learningParty(user).build();


        log.info("Instructor before saving --> {}", instructor);
        instructorRepository.save(instructor);
        assertThat(instructor.getId()).isNotNull();
        assertThat(instructor.getLearningParty().getId()).isNotNull();
        log.info("Instructor after saving --> {}", instructor);

        Instructor savedInstructor =  instructorRepository.findById(instructor.getId()).orElse(null);

        assertThat(savedInstructor).isNotNull();
        assertThat(savedInstructor.getBio()).isNull();
        assertThat(savedInstructor.getGender()).isNull();

        savedInstructor.setBio("I am a java instructor");
        savedInstructor.setGender(GENDER.FEMALE);

        instructorRepository.save(savedInstructor);

        assertThat(savedInstructor.getBio()).isNotNull();
        assertThat(savedInstructor.getGender()).isNotNull();

    }

}