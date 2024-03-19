package com.example.clubdeportivo.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Members")
public class Member implements Serializable {

    @Id
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, length = 100)
    private String address;

    @ManyToOne
    @JoinColumn(name = "sport_discipline_id")
    private SportDiscipline sportDiscipline;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Participation> participations;

    public Member() {
        this.participations = new ArrayList<>();
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public SportDiscipline getSportDiscipline() {
        return sportDiscipline;
    }

    public void setSportDiscipline(SportDiscipline sportDiscipline) {
        this.sportDiscipline = sportDiscipline;
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }
}
