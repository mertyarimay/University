package com.education.university.entity;

import jakarta.persistence.*;
import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")


public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String lastName;
    @Column(unique = true)
    private String studentNo;

    @ManyToOne
    @JoinColumn(name = "section_Id")
    private Section section;

}
