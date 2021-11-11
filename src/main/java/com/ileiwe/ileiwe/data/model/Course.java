package com.ileiwe.ileiwe.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(length = 1000)
    private String description;
    private String duration;
    private String language;
    @ElementCollection
    private List<String> imageUrls;
    @CreationTimestamp
    private LocalDateTime dateCreated;
    private LocalDateTime datePublished;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private boolean isPublished;
    @ManyToOne
    private Instructor instructor;
    @ManyToMany
    private List<Student> students;
}
