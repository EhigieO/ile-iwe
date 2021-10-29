package com.ileiwe.ileiwe.data.repository;

import com.ileiwe.ileiwe.data.model.Authority;
import com.ileiwe.ileiwe.data.model.LearningParty;
import com.ileiwe.ileiwe.data.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"/db/insert.sql"})
class LearningPartyTest {

    @Autowired
    LearningPartyRepository learningPartyRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void createLearningPartyWithStudentRole(){
        LearningParty learningUser =
                new LearningParty("yomi@gmail.com", "123lll", new Authority(Role.ROLE_STUDENT));

        learningPartyRepository.save(learningUser);

        assertThat(learningUser.getId()).isNotNull();
        assertThat(learningUser.getEmail()).isEqualTo("yomi@gmail.com");
        assertThat(learningUser.getAuthorities().get(0).getAuthority()).isEqualTo(Role.ROLE_STUDENT);
        assertThat(learningUser.getAuthorities().get(0).getId()).isNotNull();

        log.info("After saving --> {}", learningUser);

    }

    @Test
    void createLearningPartyUniqueEmailsTest() {
        //given
        LearningParty learningUser =
                new LearningParty("yomi1@gmail.com", "123lll", new Authority(Role.ROLE_STUDENT));

        learningPartyRepository.save(learningUser);

        assertThat(learningUser.getId()).isNotNull();
        assertThat(learningUser.getEmail()).isEqualTo("yomi1@gmail.com");
        assertThat(learningUser.getAuthorities().get(0).getAuthority()).isEqualTo(Role.ROLE_STUDENT);
        assertThat(learningUser.getAuthorities().get(0).getId()).isNotNull();

        //when
        LearningParty learningUserTwo =
                new LearningParty("yomi1@gmail.com", "123lll", new Authority(Role.ROLE_STUDENT));

        assertThrows(DataIntegrityViolationException.class, () -> learningPartyRepository.save(learningUserTwo));

    }

    @Test
    void learningPartyWithNullValuesTest(){
        LearningParty learningUserTwo =
                new LearningParty(null, null, new Authority(Role.ROLE_STUDENT));

        assertThrows(ConstraintViolationException.class, () -> learningPartyRepository.save(learningUserTwo));
    }

    @Test
    void learningPartyWithEmptyStringTest(){
        LearningParty learningUserTwo =
                new LearningParty(" ", "", new Authority(Role.ROLE_STUDENT));
        assertThrows(ConstraintViolationException.class, () -> learningPartyRepository.save(learningUserTwo));
    }

    @Test
    void findByUserNameTest(){
        LearningParty learningParty = learningPartyRepository.findByEmail("tomi@mail.com");
        assertThat(learningParty).isNotNull();
        assertThat(learningParty.getEmail()).isEqualTo("tomi@mail.com");
        log.info("Learning party object --> {}", learningParty);
    }

}