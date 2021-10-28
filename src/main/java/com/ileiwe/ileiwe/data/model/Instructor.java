package com.ileiwe.ileiwe.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private String firstname;

    @NotBlank
    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private String lastname;

    @Enumerated(EnumType.STRING)
    private GENDER gender;

    private String specialization;

    @Column(length = 1000)
    private String bio;

    @OneToOne(cascade = CascadeType.PERSIST)
    private LearningParty learningParty;

    @OneToMany
    private List<Course> courses;
}
