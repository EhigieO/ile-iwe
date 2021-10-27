package com.ileiwe.ileiwe.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    private LocalDate dob;

    @Transient
    private String age;

    @Enumerated(EnumType.STRING)
    private GENDER gender;

    @OneToOne
    private LearningParty learningParty;

    @ManyToMany
    private List<Course> courses;

}
