package com.example.clubdeportivo.entities;

import com.example.clubdeportivo.entities.Member;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SportDisciplines")
public class SportDiscipline implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "sportDiscipline")
    private List<Member> members;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DisciplineType type;

    public SportDiscipline() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DisciplineType getType() {
        return type;
    }

    public void setType(DisciplineType type) {
        this.type = type;
    }


}
