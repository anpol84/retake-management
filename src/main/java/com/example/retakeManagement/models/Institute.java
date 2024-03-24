package com.example.retakeManagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "institute")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Institute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Название дисциплины не должно быть пустым")
    private String name;

    @OneToMany(mappedBy = "institute")
    private List<Specialization> specializations;

    @ManyToMany(mappedBy = "institutes")
    private List<Course> courses;

    @OneToMany(mappedBy = "institute")
    private List<Department> departments;

    @Override
    public String toString() {
        return name;
    }
}
